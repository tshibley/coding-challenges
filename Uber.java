// This is the text editor interface. 
// Anything you type or change here will be seen by the other person in real time.

// [1,2,[3,4,[5]],6,7]

// import List and ArrayList 
import java.java.util.*;

public class EmbeddedList {
    List<EmbeddedList> data; 
    
    public EmbeddedList() {
        data = new ArrayList<EmbeddedList>(); 
    }
    
    public void add(EmbeddedList value) {
        data.add(value); 
    }
    
    /*public EmbeddedList get(int i) {
        data.get(i); 
    }
    
    public EmbeddedList delete(int i) {
        EmbeddedList result = data.get(i); 
        data.remove(i); 
        return result; 
    } */
    
    public List<EmbeddedList> getLists() {
        return new ArrayList(data); 
    }

    
}

public class Data extends EmbeddedList {
    int value; 
    
    public Data (int num) {
        this.value = num; 
    }
    
    public int getValue() {
        return value; 
    }
}

public class Flatten {
    public static List<Integer> flatten(EmbeddedList data) {
        List<Integer> result = new ArraytList<Integer>(); 
        return flattenHelper(result, data); 
    }
    
    private static List<Integer> flattenHelper(List<Integer> result, EmbeddedList data) {
            for(EmbeddedList list : data.getLists()) {
                result = flattenHelper(result, list); 
            }
        return result; 
    }
    
    private static List<Integer> flattenHelper(List<Integer> result, Data data) {
        result.add(data.getValue()); 
        return result; 
    }
}


public class Test {
    
    public static void main(String[] args) {
        List<Integer> result = new ArrayList<Integer>(); 
        EmbeddedList data = new EmbeddedList(); 
        EmbeddedList list1 = new EmbeddedList(); 
        EmbeddedList list2 = new EmbeddedList();
        EmbeddedList list3 = new EmbeddedList();
        data.add(new Data(4)); 
        result.add(4); 
        
        list1.add(new Data(7)); 
        list1.add(new DAta(3)); 
        result.add(7); 
        result.add(3); 
        
        list2.add(new Data(9)); 
        list2.add(new Data(1)); 
        result.add(9); 
        result.add(1); 
        
        list1.add(list2); 
        
        data.add(list1); 
        
        data.add(new Data(6)); 
        result.add(6); 
        
        list3.add(new Data(5)); 
        result.add(5); 
        
        data.add(list3); 
        
        List<Integer> flattenResult = Flatten.flatten(data); 
        
        if(result.size() != flatten.size()) {
            System.out.println("Test failed"); 
        } else {
            for(int i = 0; i <  result.size(); i++) {
                if(result.get(i) != flattenResult.get(i)) {
                    System.out.println("Test failed");
                }
            }
            System.out.println("Test succeed");
        }
    }
}



