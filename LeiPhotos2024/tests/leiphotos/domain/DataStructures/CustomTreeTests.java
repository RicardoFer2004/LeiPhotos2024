package leiphotos.domain.DataStructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import leiphotos.DataStructures.CustomTree;
/**
 * The test class for the CustomTree class
 */
public class CustomTreeTests {
    private static final Comparator<TestElement> COMPARATOR = (a,b) -> a.val - b.val;
    @Test
    void addNonExistingElementsTest(){
        TestElement t1 = new TestElement(1, 'a');
        TestElement t2 = new TestElement(2, 'b');
        CustomTree<TestElement> tree = new CustomTree<>(COMPARATOR);
        tree.add(t1);
        tree.add(t2);
        List<TestElement> list = tree.toList();
        assertEquals(2,list.size());
        assertTrue(list.contains(t1));
        assertTrue(list.contains(t2));
    }
    @Test
    void addExistingElementsTest(){
        TestElement t1 = new TestElement(1, 'a');
        CustomTree<TestElement> tree = new CustomTree<>(COMPARATOR);
        tree.add(t1);
        tree.add(t1);
        List<TestElement> list = tree.toList();
        assertEquals(1,list.size());
        assertTrue(list.contains(t1));
    }
    @Test
    void removeNonExistentElementTest(){
        CustomTree<TestElement> tree = new CustomTree<>(COMPARATOR);
        TestElement t1 = new TestElement(1, 'a');
        tree.add(t1);
        TestElement t2 = new TestElement(1, 'b');
        tree.remove(t2);
        List<TestElement> list = tree.toList();
        assertEquals(1,list.size());
        assertTrue(list.contains(t1));
    }
    @Test
    void removeExistentElementTest(){
        CustomTree<TestElement> tree = new CustomTree<>(COMPARATOR);
        TestElement t1 = new TestElement(6, 'a');
        TestElement t2 = new TestElement(7, 'b');
        TestElement t3 = new TestElement(4, 'b');
        TestElement t4 = new TestElement(2, 'b');
        TestElement t5 = new TestElement(3, 'b');
        TestElement t6 = new TestElement(5, 'b');
        tree.add(t1);
        tree.add(t2);
        tree.add(t3);
        tree.add(t4);
        tree.add(t5);
        tree.add(t6);
        tree.remove(t3);
        TestElement[] elements = {t1,t2,t4,t5,t6};
        List<TestElement> list = tree.toList();
        assertEquals(5,list.size());
        for(TestElement el : elements){
            assertTrue(list.contains(el));
        }
        for(int i = 0; i < list.size() - 1;i++){
            TestElement curr = list.get(0);
            TestElement next = list.get(1);
            assertTrue(COMPARATOR.compare(curr, next) <= 0);
        }
    }
    @Test
    void toListTest(){
        TestElement t1 = new TestElement(1, 'a');
        TestElement t2 = new TestElement(2,'a');
        TestElement t3 = new TestElement(3, 'b');
        CustomTree<TestElement> tree = new CustomTree<>(COMPARATOR);
        tree.add(t2);
        tree.add(t3);
        tree.add(t1);
        List<TestElement> list = tree.toList();
        assertEquals(3, list.size());
        TestElement[] elements = {t1,t2,t3};
        for(TestElement el : elements){
            assertTrue(list.contains(el));
        }
        for(int i = 0; i < list.size() - 1;i++){
            TestElement curr = list.get(0);
            TestElement next = list.get(1);
            assertTrue(COMPARATOR.compare(curr, next) <= 0);
        }
    }
    private class TestElement{
        int val;
        char ch;
        public TestElement(int val,char ch){
            this.val = val;
            this.ch = ch;
        }
        @Override
        public boolean equals(Object obj){
            if(obj instanceof TestElement other){
                return this.val == other.val && this.ch == other.ch;
            }
            return false;
        }
       
    }
}
