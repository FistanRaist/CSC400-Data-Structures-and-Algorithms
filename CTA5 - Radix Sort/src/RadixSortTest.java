/**
 * Test class for RadixSort algorithm.
 * Demonstrates sorting and prints step-by-step process.
 */
public class RadixSortTest {
    
    /**
     * Main method to test radix sort with given array.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Integer[] arr = {783, 99, 472, 182, 264, 543, 356, 295, 692, 491, 94};
        
        System.out.println("Original array:");
        printArray(arr);
        
        System.out.println("\nSorting process:");
        Integer[] sorted = RadixSort.sort(arr);
        
        System.out.println("\nSorted array:");
        printArray(sorted);
    }
    
    /**
     * Prints array elements.
     * @param arr Array to print
     */
    private static void printArray(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}