public class HashIntSet  implements Iterable<Integr> {
  
   // Instance fields
   private static final double MAX_LOAD_FACTOR = 0.75;
   private HashEntry[] elementData;
   private int size;
   
   // Constructor (empty set)
   public HashIntSet() {
      elementData = new HashEntry[1];
      size = 1;
   }
   
   // Hash function
   private int hashFunction(int value) {
      return Math.abs(value) % elementData.length;
   }
   
   // Add 
   public void add(int value) {
      if (!contains(value)) {
         if (loadFactor() >= MAX_LOAD_FACTOR) {
            rehash();
         }
         int bucket = hashFunction(value);
         elementData[bucket] = new HashEntry(value, elementData[bucket]);
         size++;
      }
   }
   
   // Rehash
   private void rehash() {
      HashEntry[] oldElementData = elementData;
      elementData = new HashEntry[2 * oldElementData.length];
      size = 0;
      
      for (int i = 0; i < oldElementData.length; i++) {
         HashEntry current = oldElementData[i];
         while (current != null) {
            add(current.data);
            current = current.next;
         }
      }
   }
   
   // Contains
   public boolean contains(int value) {
      int bucket = hashFunction(value);
      HashEntry current = elementData[bucket];
      while (current != null) {
         if (current.data == value) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // Remove
   public void remove(int value) {
      int bucket = hashFunction(value);
      if (elementData[bucket] != null) {
         if (elementData[bucket].data == value) {
            elementData[bucket] = elementData[bucket].next;
            size--;
         } else {
            HashEntry current = elementData[bucket];
            while (current.next != null && current.next.data != value) {
               current = current.next;
            }
            if (current.next != null) {
               current.next = current.next.next;
               size--;
            }
         }
      }
   }
   
   // Load factor
   private double loadFactor() {
      return (double) size / elementData.length;
   }
   
   // Iterator
   @Override
   public Iterator<Integer> iterator() {
      return new HashIterator();
   }
   
   // Private inner class
   private class HashEntry {
      
      // Instance fields
      private int data;
      private HashEntry next;
      
      // Constructor (default)
      public HashEntry(int data) {
         this(data, null);
      }
      
      // Constructor (not default)
      public HashEntry(int data, HashEntry next) {
         this.data = data;
         this.next = next;
      }
   }
   
   // Private inner class
   private class HashIterator implements Iterator<Integer> {
      
      // Instance fields
      private int currentBucket;
      private HashEntry currentNode;
      
      // Constructor
      public HashIterator() {
         currentBucket = 0;
         currentNode = null;
         
         while (currentBucket < elementData.length && elementData[currentBucket] == null) {
            currentBucket++;
         }
         
         if (currentBucket < elementData.length) {
            currentNode = elementData[currentBucket];
         }
      }
      
      // Has next 
      public boolean hasNext() {
         if (currentNode != null && currentNode.next != null) {
            return true;
         } else {
            for (int i = currentBucket + 1; i < elementData.length; i++) {
               if (elementData[i] != null) {
                  return true;
               }
            }
            return false;
         }
      }
      
      // Next
      public int next() {
         int currentData = currentNode.data;
         if (currentNode.next != null) {
            currentNode = currentNode.next;
         } else {
            boolean found = false;
            for (int i = currentBucket + 1; i < elementData.length; i++) {
               if (elementData[i] != null) {
                  currentBucket = i;
                  currentNode = elementData[currentBucket];
                  found = true;
                  break;
               }
            }
            if (!found) currentNode = null;
         }
         return currentData;
      }
   }
}