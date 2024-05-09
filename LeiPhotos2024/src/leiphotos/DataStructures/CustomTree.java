package leiphotos.DataStructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * A tree like structure that allows for pseudo-duplicate elements, according to the comparator.
 * This means that if the given comparator determines that the element to be added is equal
 * to an already existing element in the tree, the element can still be added as long as
 * it is not truly equal to any existing element (i.e,as long as !elementToAdd.equals(existingElement))
 * 
 * The ordering of the elements is done according to the given comparator
 * 
 * Supported Operations: Add and remove
 */
public class CustomTree<E> {
    private final Comparator<E> comparator;
    private Node head;
    public CustomTree(Comparator<E> comp){
        this.comparator = comp;
    }
    /**
     * Adds an element to the tree
     * @param item The element to add
     * @requires item != null
     */
    public void add(E item){
        Node node = new Node(item);
        this.head = addNode(node,head);
    }
    /**
     * Adds a node to the tree, if it does not already exist
     * @param node The node to add
     * @param curr The head of the tree
     * @return The updated head of the tree
     */
    private Node addNode(Node node,Node curr){
        if(curr == null){
            return node;
        }
        if(curr.item.equals(node.item)){
            return curr;
        }
        int cmp = comparator.compare(node.item,curr.item);
        if(cmp < 0 || cmp == 0){
            curr.left = addNode(node,curr.left);
        }else{
            curr.right = addNode(node,curr.right);
        }
        return curr;
    }
    /**
     * Removes an element from the tree, if it exists
     * @param item The element to be removed
     * @requires {@code item != null}
     */
    public void remove(E item){
        this.head = remove(item,head);
    }
    /**
     * Removes an element from the tree, if it exists
     * @param item The element to be removed
     * @param curr The head of the tree
     * @return The updated head of the tree
     */
    private Node remove(E item,Node curr){
        if(curr == null){
            return null;
        }
        int cmp = comparator.compare(item, curr.item);
        if(cmp < 0){
            curr.left = remove(item,curr.left);
        }else if(cmp == 0){
            if(item.equals(curr.item)){
                Node right = curr.right;
                Node node = curr.left;
                if(node == null){
                    node = right;
                }else if(right != null){
                    /**
                     * For some reason, sonar lint wants this line to be 
                     * removed but it cannot be removed, otherwise the function would be wrong
                     */
                    node = addNode(right,node);
                }
                return node;
            }else{
                curr.left = remove(item,curr.left);
            }
        }else{
            curr.right = remove(item,curr.right);
        }
        return curr;
    }
    /**
     * Gets the list containing all the tree's elements in order according to the comparator
     * @return The list containg all the tree's elements in order
     * @ensures \result != null
     */
    public List<E> toList(){
        List<E> list = new ArrayList<>();
        inOrderTraversal(head,list);
        return list;
    }
    /**
     * Puts the tree's nodes' item into the given list.
     * The elements will be put into the list in order.
     * @param curr The head of the tree (i.e. the starting node)
     * @param list The list in which the elements will be stored
     * @requires {@code list != null}
     */
    private void inOrderTraversal(Node curr,List<E> list){
        if(curr == null){
            return;
        }
        inOrderTraversal(curr.left, list);
        list.add(curr.item);
        inOrderTraversal(curr.right, list);
    }
    private class Node{
        E item;
        Node left;
        Node right;
        public Node(E item){
            this.item = item;
        }
    }
}
