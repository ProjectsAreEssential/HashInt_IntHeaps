import java.util.Arrays;
import java.util.NoSuchElementException;

public class HeapIntPriorityQueue3 {

   // Instance fields
   private int[] elementData;
   private int size;

   // Constructor (default)
   public HeapIntPriorityQueue3() {
      this(10);
   }

   // Constructor (capacity) 
   public HeapIntPriorityQueue3(int capacity) {
      elementData = new int[capacity];
      size = 0;
   }

   // Parent node
   private int parent(int index) {
      return index / 3;
   }

   // Left child node
   private int leftChild(int index) {
      return 3 * index - 1;
   }

   // Middle child node
   private int middleChild(int index) {
      return 3 * index;
   }

   // Right child node
   private int rightChild(int index) {
      return 3 * index + 1;
   }

   // Has parent
   private boolean hasParent(int index) {
      return index > 1;
   }

   // Has left child
   private boolean hasLeftChild(int index) {
      return leftChild(index) <= size;
   }

   // Has middle child
   private boolean hasMiddleChild(int index) {
      return middleChild(index) <= size;
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

      size++;
      elementData[size] = value;

      int index = size;
      while (hasParent(index) && elementData[index] < elementData[parent(index)]) {
         int parentIndex = parent(index);
         swap(elementData, index, parentIndex);
         index = parentIndex;
      }
   }

   // Remove
   public int remove() {
      if (size == 0) {
         throw new NoSuchElementException();
      }

      int result = elementData[1];

      elementData[1] = elementData[size];
      size--;

      int index = 1;
      while (hasLeftChild(index)) {
         int smallestChild = leftChild(index);

         if (hasMiddleChild(index) && elementData[middleChild(index)] < elementData[smallestChild]) {
            smallestChild = middleChild(index);
         }
         if (hasRightChild(index) && elementData[rightChild(index)] < elementData[smallestChild]) {
            smallestChild = rightChild(index);
         }

         if (elementData[smallestChild] < elementData[index]) {
            swap(elementData, index, smallestChild);
            index = smallestChild;
         } else {
            break;
         }
      }

      return result;
   }
}
