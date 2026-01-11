import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Debug {

    public static void main(String[] args) {
        System.out.println("Debugging");
        mp();
        list();
    }



    public static void mp(){
        int[] a = {1,9,3,4,5,8,16,66,77,18,9};
        int n = a.length;
        for (int i = 0; i < a.length; i++){
            for (int j = 0; j < n-1-i; j++) {
               if(a[j] > a[j+1]){
                  int temp =  a[j];
                  a[j] = a[j+1];
                  a[j+1] = temp;
               }
            }
        }
        System.out.println(Arrays.toString(a));
    }

    public static void list(){
        ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("one", "two", "three"));
        list1.sort(Comparator.comparing(str->str.length(), Comparator.reverseOrder()));
        System.out.println(list1);
    }
}
