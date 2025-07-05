/**
 * Implements radix sort algorithm for sorting Integer objects.
 * Uses least significant digit (LSD) radix sort with counting sort as subroutine.
 */
public class RadixSort {
    
    /**
     * Sorts an array of Integers using radix sort.
     * @param arr Array of Integers to be sorted
     * @return Sorted array
     */
    public static Integer[] sort(Integer[] arr) {
        if (arr == null || arr.length <= 1) return arr;
        
        // Find maximum number to determine number of digits
        int max = getMax(arr);
        
        // Process each digit place (ones, tens, hundreds, etc.)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
        
        return arr;
    }
    
    /**
     * Finds maximum value in array.
     * @param arr Array to search
     * @return Maximum value
     */
    private static int getMax(Integer[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }
    
    /**
     * Performs counting sort on array based on specific digit.
     * @param arr Array to sort
     * @param exp Current digit place (1, 10, 100, etc.)
     */
    private static void countingSortByDigit(Integer[] arr, int exp) {
        int n = arr.length;
        Integer[] output = new Integer[n];
        int[] count = new int[10];
        
        // Initialize count array
        for (int i = 0; i < 10; i++) {
            count[i] = 0;
        }
        
        // Count occurrences of each digit
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }
        
        // Compute cumulative count
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }
        
        // Manually copy output to original array
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
}