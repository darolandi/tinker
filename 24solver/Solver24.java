import java.util.ArrayList;
import java.util.Scanner;

/**
 * Outputs one out of many solutions for the 24 card game.
 * 
 * @author Daniel Rolandi
 * @version 5/52013
 */
public class Solver24{
  private int[] source = {1, 8, 8, 8};
  private ArrayList<Integer> ar;
  private boolean found;
  
  /** The actual main. */
  public Solver24(){
    ar = new ArrayList<Integer>();
    Scanner console = new Scanner(System.in);
    int input;
        
    System.out.println("Give 4 numbers (1-13 inclusive)(0 to exit)!");
    for(int i=0; i<4; i++){      
      while(!console.hasNextInt()){
        System.out.println("Bad input!");
        console.next();
      }
      input = console.nextInt();
      if(input == 0){
        System.out.println("Exitcode received. Ending.");
        return;
      }
      if(! validate(input)){
        System.out.println("Bad input!");
        i--;
        continue;
      }
      ar.add(input);
    }        
    printArray(ar);
    
    String result = "";
    found = false;
    result += traverse(ar, "");
    
    if(!found){
      System.out.println("NO SOLUTION");
    }else{
      System.out.println(result);
    }    
  }
  
  private boolean validate(int n){
    return (1 <= n) && (n <= 13);
  }
  
  private String traverse(ArrayList<Integer> ar, String path){
    if(ar.size() == 1 && ar.get(0) == 24){
      found = true;
      return path;
    }else if(ar.size() == 1 && ar.get(0) != 24){
      return "";
    }
    for(int i = 0; i < (ar.size()-1); i++){
      for(int k = i+1; k < ar.size(); k++){
        int left = ar.remove(i);
        int right = ar.remove(k-1);
        ArrayList<Integer> localList;
        String step;
        
        // ADDITION
        localList = cloneArray(ar);        
        localList.add(left + right);
        step = traverse(localList, path + String.format("(%d + %d = %d); ", left, right, left+right));
        if(found){
          return step;
        }
        
        // SUBTRACTION
        localList = cloneArray(ar);
        localList.add(left - right);
        step = traverse(localList, path + String.format("(%d - %d = %d); ", left, right, left-right));
        if(found){
          return step;
        }
        
        // MULTIPLICATION
        localList = cloneArray(ar);        
        localList.add(left * right);
        step = traverse(localList, path + String.format("(%d * %d = %d); ", left, right, left*right));
        if(found){
          return step;
        }
          
        // DIVISION
        if(right != 0 && left%right == 0){
          localList = cloneArray(ar);          
          localList.add(left / right);          
          step = traverse(localList, path + String.format("(%d / %d = %d); ", left, right, left/right));
          if(found){
            return step;
          }
        }
        ar.add(left);
        ar.add(right);
      }
    }
    return "";
  }
  
  private ArrayList<Integer> cloneArray(ArrayList<Integer> ar){
    ArrayList<Integer> newList = new ArrayList<Integer>();
    for(int i = 0; i < ar.size(); i++){
      newList.add(ar.get(i));
    }
    return newList;
  }
  
  private void printArray(ArrayList<Integer> ar){
    for(int i = 0; i < ar.size(); i++){
      System.out.print(ar.get(i) + " ");
    }
    System.out.println();
  }
  
  /** Application method. */
  public static void main(String[] args){
    new Solver24();
  }
  
}