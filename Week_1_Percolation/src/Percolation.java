public class Percolation {
    // Private Members
    private Cell[][] grid;
    private Cell topVirtualSite;
    private Cell bottomVirtualSite;
    private WeightedQuickUnionUF UF;
    private int size;
    
    // Cell Inner Class
    private static class Cell {
        private int UFIndex;
        private boolean open;       
        
        Cell(int index) {
            open = false;
            UFIndex = index;
        }
        
        int getUFIndex() {
            return UFIndex;
        }        
        
        boolean isOpen() {
            return open;
        }
        
        void setOpen(boolean value) {
            open = value;
        }
    }
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("The grid size has to be greater than 0.");
        }
        
        /* Initialize grid of size N + 1 by N + 1 since indexing begins at (1, 1).
         * We simply do not use the first row and column. The tradeoff of memory for
         * readability is acceptable.
         */
        grid = new Cell[N + 1][N + 1];
        size = N;
        UF = new WeightedQuickUnionUF(N * N + 2); // + 2 for the virtual sites
        
        // Initialize the grid  with cells and virtual sites with default indexes
        topVirtualSite = new Cell(N * N);
        topVirtualSite.setOpen(true);        
        bottomVirtualSite = new Cell(N * N + 1);
        bottomVirtualSite.setOpen(true);
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                int UFIndex = calculateUFIndex(i, j);
                grid[i][j] = new Cell(UFIndex);
            }
         }
        
        // Connect the top and bottom row to virtual sites
        connectToVirtualSites();
    }   
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validate(i, j);
        
        if (!grid[i][j].isOpen()) {
            connectToOpenNeighbors(i, j);
            grid[i][j].setOpen(true);
        }        
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i, j);
        
        return grid[i][j].isOpen();
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validate(i, j);
        
        // to exclude the closed top and bottom connected sites
        if (!isOpen(i, j)) {
            return false;
        }
        
        int cellUFIndex = calculateUFIndex(i, j);
        
        return UF.connected(cellUFIndex, topVirtualSite.getUFIndex());
    }
    
    // does the system percolate?
    public boolean percolates() {
        return UF.connected(bottomVirtualSite.getUFIndex(), topVirtualSite.getUFIndex());
    }
    
    // Private Methods
    private void validate(int i, int j) {
        if (!isInBounds(i) || !isInBounds(j)) {
            throw new java.lang.IndexOutOfBoundsException("Invalid index specified");
        }
    }
    
    private boolean isInBounds(int i) {
        return (i >= 1 && i <= size);
    }
    
    // get the index for (row, column j)
    private int calculateUFIndex(int i, int j) {
        validate(i, j);
        
        // change to 0 indexing to make calculation easier
        i = i - 1;
        j = j - 1;
        
        return (size * i) + j;
    }
    
    private void connectToOpenNeighbors(int i, int j) {
        int cellUFIndex = calculateUFIndex(i, j);
        int adjacentCellUFIndex;
        
        // left cell
        int leftCellRow = i;
        int leftCellCol = j - 1;      
        
        if (isInBounds(leftCellRow) && isInBounds(leftCellCol) && grid[leftCellRow][leftCellCol].isOpen()) {
            adjacentCellUFIndex = calculateUFIndex(leftCellRow, leftCellCol);
            UF.union(cellUFIndex, adjacentCellUFIndex);
        }
        
        // top cell
        int topCellRow = i - 1;
        int topCellCol = j;      
        
        if (isInBounds(topCellRow) && isInBounds(topCellCol) && grid[topCellRow][topCellCol].isOpen()) {
            adjacentCellUFIndex = calculateUFIndex(topCellRow, topCellCol);
            UF.union(cellUFIndex, adjacentCellUFIndex);
        }
        
        // right cell
        int rightCellRow = i;
        int rightCellCol = j + 1;      
        
        if (isInBounds(rightCellRow) && isInBounds(rightCellCol) && grid[rightCellRow][rightCellCol].isOpen()) {
            adjacentCellUFIndex = calculateUFIndex(rightCellRow, rightCellCol);
            UF.union(cellUFIndex, adjacentCellUFIndex);
        }
        
        // bottom cell
        int bottomCellRow = i + 1;
        int bottomCellCol = j;      
        
        if (isInBounds(bottomCellRow) && isInBounds(bottomCellCol) && grid[bottomCellRow][bottomCellCol].isOpen()) {
            adjacentCellUFIndex = calculateUFIndex(bottomCellRow, bottomCellCol);
            UF.union(cellUFIndex, adjacentCellUFIndex);
        }
    }
    
    private void connectToVirtualSites() {
        for (int i = 1; i <= size; i++) {
            int topRowCellUFIndex = calculateUFIndex(1, i);
            UF.union(topRowCellUFIndex, topVirtualSite.getUFIndex());
            
            int bottomRowCellUFIndex = calculateUFIndex(size, i);
            UF.union(bottomRowCellUFIndex, bottomVirtualSite.getUFIndex());
        }
    }
    
    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);   
        
        StdOut.println(percolation.isFull(1, 1));            
        StdOut.println(percolation.percolates());
        
        percolation.open(1, 1);
        percolation.open(2, 2);
        StdOut.println(percolation.percolates());
        
        percolation.open(1, 2);
        StdOut.println(percolation.percolates());
    }
}
