public class WeightedUnionFind {
    // Instance variables
    private int[] parent;
    private int[] size;
    private int count;

    // Constructor
    public WeightedUnionFind(int N) {
        count = N;
        parent = new int[N];
        size = new int[N];

        // Initialize the parent with each element pointing to self
        // and the size array with a size of 1.
        for (int i = 0; i < count; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    // Methods
    /*
     * Performs the union operation on index p and q
     */
    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);

        if (rootP == rootQ)
            return;

        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[q] += size[p];
        } else {
            parent[rootQ] = rootP;
            size[p] += size[q];
        }
    }

    /*
     * Checks if elements p and q are connected
     */
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    /*
     * Returns the root of the element
     */
    private int root(int index) {
        while (index != parent[index]) {
            index = parent[index];
        }

        return parent[index];
    }

    /*
     * Display the array
     */
    public void displayArray() {
        for (int i = 0; i < count; i++) {
            System.out.print(parent[i] + " ");
        }

        System.out.println("");
    }

    public static void main(String[] args) {
        WeightedUnionFind w = new WeightedUnionFind(10);

        w.union(4, 0);
        w.union(0, 3);
        w.union(4, 5);
        w.union(6, 2);
        w.union(0, 7);
        w.union(9, 8);
        w.union(9, 2);
        w.union(4, 6);
        w.union(1, 9);

        w.displayArray();
    }

}
