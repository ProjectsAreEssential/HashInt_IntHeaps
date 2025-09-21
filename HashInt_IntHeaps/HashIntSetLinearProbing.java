public class HashIntSetLinearProbing {
   
   // Instance fields
   private static final int REMOVED = Integer.MAX_VALUE;
   private static final int EMPTY = Integer.MIN_VALUE;
   private static final double MAX_LOAD_FACTOR = 0.75;
   private int[] elementData;
   private int size;

   // Constructor
   public HashIntSetLinearProbing() {
      elementData = new int[10];
      for (int i = 0; i < elementData.length; i++) {
         elementData[i] = EMPTY;
      }
      size = 0;
   }
   
   // Add
   public void add(int value) {
      if (!contains(value)) {
         if (loadFactor() >= MAX_LOAD_FACTOR) {
            rehash();
         }
         int bucket = hashFunction(value);
         while (elementData[bucket] != EMPTY && elementData[bucket] != REMOVED) {
            bucket = (bucket + 1) % elementData.length;
         }
         elementData[bucket] = value;
         size++;      
      }
   }
   
   // Contains
   public boolean contains(int value) {
      int bucket = hashFunction(value);
      if (elementData[bucket] == value) {
         return true;  
      } else if (elementData[bucket] == EMPTY) {
         return false;      
      } else {
         while (elementData[bucket] != value && elementData[bucket] != EMPTY) {
            bucket = (bucket + 1) % elementData.length;
         }
         return elementData[bucket] == value;
      }
   }
   
   // Remove
   public void remove(int value) {
      int bucket = hashFunction(value);
      while (elementData[bucket] != EMPTY) {
         if (elementData[bucket] == value) {
            elementData[bucket] = REMOVED;
            size--;
            break;
         } else {
            bucket = (bucket + 1) % elementData.length;
         }
      }
   }
   
   // Hash function
   private int hashFunction(int value) {
      return Math.abs(value) % elementData.length;
   }
   
   // Load factor
   private double loadFactor() {
      return (double) size / elementData.length;
   }
   
   // Rehash
   private void rehash() {
      int[] newElementData = new int[elementData.length * 2];
      for (int i = 0; i < newElementData.length; i++) {
         newElementData[i] = EMPTY;
      }
      size = 0;
      
      for (int n : elementData) {
         if (n != EMPTY && n != REMOVED) {
            int bucket = Math.abs(n) % newElementData.length;
            while (newElementData[bucket] != EMPTY) {
               bucket = (bucket + 1) % newElementData.length;
            }
            
            newElementData[bucket] = n;
            size++;
         }
      }
      elementData = newElementData;
   }
}