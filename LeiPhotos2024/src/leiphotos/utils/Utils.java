package leiphotos.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class Utils {
    private Utils(){
    }
    /**
     * Reverses the given string
     * @param str The given string
     * @requires str != null
     * @ensures {@code reversedString.length() == str.length() && reversedString.charAt(i)== str.charAt(str.length() - 1 - i), 0 <= i < str.length()}
     * @return The reversed string
     */
    public static String reverse(String str){
        StringBuilder sb = new StringBuilder();
        for(int i = str.length() - 1;i >= 0;i--){
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
    /**
     * Separates the given number with a character 
     * per 3 digits with the given separator
     * Example: num = 1234567 result = 1_234_567
     * @param separator The separator
     * @param num The given number
     * @return A string containing the number separated by the given separator
     */
    public static String separateNumber(char separator,long num){
        String numStr = Long.toString(num);
        return interspose(separator,3,reverse(numStr));
    }
    /**
     * Adds the given character per interval to the given string.  
     * @param ch The given character
     * @param interval The interval that decided when the character is placed
     * @param str The given string
     * @return A string with original string contents separated by the given character per interval
     */
    public static String interspose(char ch,int interval,String str){
        StringBuilder sb = new StringBuilder();
        interspose(ch, interval, str,0,0,sb);
        return sb.toString();
    }
    private static void interspose(char ch,int interval,String str,
                                    int counter,int index,StringBuilder sb){
        if(index >= str.length()){
            return;
        }
        if(counter == interval){
            interspose(ch,interval,str,0,index,sb);
            sb.append(ch);
        }else{
            interspose(ch,interval,str,counter + 1,index + 1,sb);
            sb.append(str.charAt(index));
        }
    }
    /**
     * Filters the given collection according to the given predicate
     * @param <E> The object type
     * @param collection The collection to filter
     * @param predicate The given predicate
     * @requires {@code collection != null && predicate != null}
     * @return The filtered immutable list
     */
    public static <E> Collection<E> filterCollection(Collection<E> collection,Predicate<E> predicate){
        return collection.stream().filter(predicate).toList();
    }
    /**
     * Applies the given function over 
     * all the given collection's elements.
     * If all of the operations were succesful, it returns true.
     * @param <E> The object type
     * @param collection The given collection
     * @param f The given function
     * @requires {@code collection != null && f != null}
     * @return True if all of the operations were successful, false otherwise.If no operations were executed, returns false.
     */
    public static <E> boolean mapOverCollection(Collection<E> collection,Predicate<E> pred){
        if(collection.isEmpty()){
            return false;
        }
        boolean success = true;
        for(E element : collection){
            if(!pred.test(element)){
                success = false;
            }
        }
        return success;
    }
    /**
     * Puts all the elements in the given collection
     * into a list
     * @param <E> The object type
     * @param collection The given collection
     * @requires {@code collection != null}
     * @return The list containing all the given collection's elements
     * @ensures \result != null
     */
    public static <E> List<E> collectionToList(Collection<E> collection){
        List<E> list = new ArrayList<>();
        for(E element : collection){
            list.add(element);
        }
        return list;
    }
    /**
     * Replaces each character present in the original string
     * with the character,in the same position, in the regex string.
     * The only exception to this is if the character in the regex
     * string is ".". If this occurrs,the character in the original string
     * will not be replaced.
     * The resulting string will always have the same length as the smallest
     * string
     * @param str The original string
     * @param regex The regex
     * @requires {@code str != null && regex != null}
     * @return The string according to the regex
     * @ensures \result.length() == min(str.length(),regex.length())
     */
    public static String formatString(String str,String regex){
        StringBuilder sb = new StringBuilder();
        int min = Math.min(str.length(),regex.length());
        for(int i = 0; i < min;i++){
            char strCh = str.charAt(i);
            char regexCh = regex.charAt(i);
            sb.append(regexCh == '.' ? strCh : regexCh);
        }
        return sb.toString();
    }
}
