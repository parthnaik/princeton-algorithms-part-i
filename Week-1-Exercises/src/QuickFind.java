public class QuickFind {
    // Instance variables
    private int[] id;

    // Constructor
    public QuickFind(int N) {
        id = new int[N];

        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    // Methods
    /*
     * Performs the union operation on index p and q.
     */
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    /*
     * Checks if elements p and q are connected.
     */
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    /*
     * Display the array
     */
    public void displayArray() {
        for (int i = 0; i < id.length; i++) {
            System.out.print(id[i] + " ");
        }

        System.out.println("");
    }

    /*
     * Program Run
     */
    public static void main(String[] args) {
        QuickFind q = new QuickFind(10);
        q.union(5, 0);
        q.union(5, 3);
        q.union(7, 4);
        q.union(5, 9);
        q.union(1, 3);
        q.union(9, 2);

        q.displayArray();
    }
}
