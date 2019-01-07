// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int[] solution(String A, String B) {
        // write your code in Java SE 8
        String[] arrayA = A.split("\\s+");
        String[] arrayB = B.split("\\s+");
        int[] arrACounts = new int[arrayA.length];
        for(int i = 0; i < arrACounts.length; i++) {
            arrACounts[i] = getSmallCharFreq(arrayA[i]);
        }
        
        int[] result = new int[arrayB.length];
        for(int i = 0; i < result.length; i++){
            int bFreq = getSmallCharFreq(arrayB[i]);
            for(int j = 0; j < arrACounts.length; j++){
                if(arrACounts[j] < bFreq) {
                    result[i]++; 
                }
            }
        }
        return result; 
    }
    
    
    private static int getSmallCharFreq(String s) {
        char small = getSmallestCharacter(s);
        int result = getCharacterCount(s, small);
        return result;
    }
    
    private static char getSmallestCharacter(String s) {
        char result = s.charAt(0);
        for(int i = 1; i < s.length(); i++){
            if(s.charAt(i) < result) {
                result = s.charAt(i);
            }
        }
        return result; 
    }
    
    private static int getCharacterCount(String s, char c) {
        int result = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == c){
                result++; 
            }
        }
        return result; 
    }
}