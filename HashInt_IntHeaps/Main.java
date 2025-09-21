import java.util.*;

public class Main {
   public static void main(String[] args) {
      Queue<Integer> pq = new PriorityQueue<>();
      pq.add(1);
      pq.add(10);
      pq.add(-22);
      pq.add(3);
      
      while (!pq.isEmpty()) {
         System.out.println(pq.remove());
      }
   }
   
   // Heap sort
   public static void heapSort(int[] a) {
      Queue<Integer> pq = new PriorityQueue<>();
      for (int n : a) {
         pq.add(n);
      }
      for (int i = 0; i < a.length; i++) {
         a[i] = pq.remove();
      }
   }
}