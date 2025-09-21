public class HeapIntPriorityQueue {
   
   // Instance fields
   private int[] elementData;
   private int size;
   private boolean isMinHeap;
   
   // Constructor (max heap)
   public HeapIntPriorityQueue(boolean isMinHeap) {
      elementData = new int[10];
      size = 0;
      this.isMinHeap = isMinHeap;
   }
   
   // Constructor (min heap)
   public HeapIntPriorityQueue(boolean isMinHeap) {
      this(true);
   }
   
   // Parent node
   private int parent(int index) {
      return index / 2;
   }
   
   // Left child node
   private int leftChild(int index) {
      return index * 2;
   }
   
   // Right child node
   private int rightChild(int index) {
      return index * 2 + 1;
   }
   
   // Has parent
   private boolean hasParent(int index) {
      return index > 1;
   }
   
   // Has left child
   private boolean hasLeftChild(int index) {
      return leftChild(index) <= size;
   }
   
   // Has right child
   private boolean hasRightChild(int index) {
      return rightChild(index) <= size;
   }
   
   // Swap
   private void swap(int[] a, int index1, int index2) {
      int temp = a[index1];
      a[index1] = a[index2];
      a[index2] = temp;
   }
   
   // Add
   public void add(int value) {
      if (size + 1 >= elementData.length) {
         elementData = Arrays.copyOf(elementData, elementData.length * 2);
      }
      
      elementData[size + 1] = value;
      
      int index = size + 1;
      boolean found = false;
      while (!found && hasParent(index)) {
         int parent = parent(index);
         if ((isMinHeap && elementData[index] < elementData[parent]) || 
            (!isMinHeap && elementData[index] > elementData[parent])) {
            swap(elementData, index, parent);
            index = parent(index);
         } else {
            found = true;
         }
      }
      size++;
   }
   
   // Remove
   public int remove() {
      int result = peek();
      elementData[1] = elementData[size];
      size--;
      
      int index = 1;
      boolean found = false;
      while (!found && hasLeftChild(index)) {
         int left = leftChild(index);
         int right = rightChild(index);
         int child = left;
         if (hasRightChild(index)) {
             if ((isMinHeap && elementData[right] < elementData[left]) ||
                (!isMinHeap && elementData[right] > elementData[left])) {
                child = right;
             }
         }
         
         if ((isMinHeap && elementData[index] > elementData[child]) ||
            (!isMinHeap && elementData[index] < elementData[child])) {
            swap(elementData, index, child);
            index = child;
         } else {
             found = true;
         }
     }    
       
     return result;
   }
}