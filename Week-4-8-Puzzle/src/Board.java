import java.util.Arrays;

public class Board {
    
    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("puzzle04.txt");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        
        Board initial = new Board(blocks);
        StdOut.print(initial.toString());
        initial.swap(blocks, 2, 0, 2, 1);
        StdOut.print(initial.toString());
        
    }
    
    // Instance variables
    private final int[][] tiles;
    private final int N;
    
    /* Public Methods */
    // construct a board from an N-by-N array of blocks
    public Board(int[][] blocks) {
        N = blocks.length;
        tiles = deepCopy(blocks, N);
    }
    
    // board dimension N
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // ignore blank block
                if (tiles[i][j] == 0) {
                    continue;
                } else if (tiles[i][j] != getGoalValue(i, j)) {
                    hamming++;
                }
            }
        }
        
        return hamming;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // ignore blank block
                if (tiles[i][j] == 0) {
                    continue;
                } else if (tiles[i][j] != getGoalValue(i, j)) {
                    int rowToGoalDiff = Math.abs(i - getGoalRow(tiles[i][j]));
                    int colToGoalDiff = Math.abs(j - getGoalCol(tiles[i][j]));
                    
                    manhattan += (rowToGoalDiff + colToGoalDiff);
                }
            }
        }
        
        return manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }
    
    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] cloneTiles = cloneBoardTiles();
        
        int i = 0;
        int j = 0;
        
        if (cloneTiles[i][j] != 0 && cloneTiles[i][j + 1] !=0) {
            swap(cloneTiles, i, j, i, j + 1);
        } else {
            swap(cloneTiles, i + 1, j, i + 1, j + 1);
        }
        
        return new Board(cloneTiles);
    }
    
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y;
        
        return (this.N == that.N) && (Arrays.deepEquals(this.tiles, that.tiles));
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        int blankLocation[] = getBlankLocation();
        int blankTileRow = blankLocation[0];
        int blankTileCol = blankLocation[1];
        
        // 4 possible swaps
        if (isValidTile(blankTileRow - 1, blankTileCol)) {
            neighbors.push(getSwappedBoard(blankTileRow, blankTileCol, blankTileRow - 1, blankTileCol));
        }
        
        if (isValidTile(blankTileRow + 1, blankTileCol)) {
            neighbors.push(getSwappedBoard(blankTileRow, blankTileCol, blankTileRow + 1, blankTileCol));
        }
        
        if (isValidTile(blankTileRow, blankTileCol - 1)) {
            neighbors.push(getSwappedBoard(blankTileRow, blankTileCol, blankTileRow, blankTileCol - 1));
        }
        
        if (isValidTile(blankTileRow, blankTileCol + 1)) {
            neighbors.push(getSwappedBoard(blankTileRow, blankTileCol, blankTileRow, blankTileCol + 1));
        }
        
        return neighbors;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        
        return s.toString();
    }
    
    /* Private Methods */
    private int getGoalValue(int row, int col) {        
        return N * row + col + 1;
    }
    
    private int getGoalRow(int value) {
        return (value - 1) / N;
    }
    
    private int getGoalCol(int value) {
        return (value - 1) % N;
    }
    
    private void swap(int[][] a, int i, int j, int k, int l) {
        int temp = a[i][j];
        a[i][j] = a[k][l];
        a[k][l] = temp;
    }
    
    private int[][] cloneBoardTiles() {
        return deepCopy(tiles, N);
    }
    
    private boolean isValidTile(int row, int col) {
        if (row >= 0 && row < N && col >= 0 && col < N) {
            return true;
        }
        
        return false;
    }
    
    private int[] getBlankLocation() {
        int[] location = new int[2];
        
        // find blank tile
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    location[0] = i;
                    location[1] = j;
                    break;
                }
            }
        }
        
        return location;
    }
    
    // swap tile (i, j) with (k, l)
    private Board getSwappedBoard(int i, int j, int k, int l) {
        int[][] cloneBoardTiles = cloneBoardTiles();
        swap(cloneBoardTiles, i, j, k, l);
        return new Board(cloneBoardTiles);
    }
    
    // create deep copy of tiles
    private int[][] deepCopy(int[][] in, int N) {
        int[][] clone = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                clone[i][j] = in[i][j];
            }
        }
        
        return clone;
    }
}
