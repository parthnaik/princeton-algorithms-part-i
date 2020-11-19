@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class QuickSort {
    // Should not instantiate this class
    private QuickSort() {}
    
    // Always shuffle the array first to get consistent performance
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }
    
    // Recursive sort on each side of the partition key
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
        assert isSorted(a, lo, hi);
    }
    
    // Choose random key from array and partition based on key v
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        
        // find item to swap
        while (true) {
            while (less(a[++i], v))
                if (i == hi) break;
            
            while (less(v, a[--j]))
                if (j == lo) break;
              
            // Break if pointers cross
            if (i >= j) break;
            
            exch(a, i , j);
        }
        
        // place v correctly in the middle
        exch(a, lo, j);
        
        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }
    

    /* Helper Functions */
     // is v < w ?
     private static boolean less(Comparable v, Comparable w) {
         return (v.compareTo(w) < 0);
     }
         
     // exchange a[i] and a[j]
     private static void exch(Object[] a, int i, int j) {
         Object swap = a[i];
         a[i] = a[j];
         a[j] = swap;
     }

     /* Debugging Functions */
     private static boolean isSorted(Comparable[] a) {
         return isSorted(a, 0, a.length - 1);
     }

     private static boolean isSorted(Comparable[] a, int lo, int hi) {
         for (int i = lo + 1; i <= hi; i++)
             if (less(a[i], a[i-1])) return false;
         return true;
     }


     // print array to standard output
     private static void show(Comparable[] a) {
         for (int i = 0; i < a.length; i++) {
             StdOut.println(a[i]);
         }
     }
     
     public static void main(String[] args) {
//         int[] userInput = StdIn.readAllInts();
//         Integer[] a = new Integer[userInput.length];
//         
//         for (int i = 0; i < userInput.length; i++) {
//             a[i] = userInput[i];
//         }
//         
//         QuickSort.sort(a);
//         
//         show(a);
         Integer[] c = { 72, 42, 24, 99, 44, 41, 93, 98, 36, 51, 86, 11 };
         partition(c, 0, c.length - 1);
         
         for (int i = 0; i < c.length; i++) {
             StdOut.print(c[i] + " ");
         }
     }
}
