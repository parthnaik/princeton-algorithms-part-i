public class Solver {
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please provide a valid filename as the only argument.");
        }
        
        long startTime = System.nanoTime();
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000000;
        
        StdOut.println(duration);
    }
    
    /* Instance Variables */
    private boolean isSolvable;
    private int totalMoves;
    private Node last;
    
    /* Public Methods */
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // Initialize the priority queue
        MinPQ<Node> pQueue = new MinPQ<Node>();
        Node start = new Node(initial, 0, null);
        pQueue.insert(start);
        
        // Initialize the priority queue for the twin
        MinPQ<Node> pQueueTwin = new MinPQ<Node>();
        Node startTwin = new Node(initial.twin(), 0, null);
        pQueueTwin.insert(startTwin);
        
        while (true) {
            // Solve to find solution
            Node node = pQueue.delMin();
            
            if (node.board.isGoal()) {
                last = node;
                isSolvable = true;
                totalMoves = node.moves;
                break;
            }
            
            enqueue(node, pQueue);
            
            // Similarly solve for twin. If twin is solvable then original is not, and vice-versa.
            Node nodeTwin = pQueueTwin.delMin();
            
            if (nodeTwin.board.isGoal()) {
                isSolvable = false;
                totalMoves = -1;
                break;
            }
            
            enqueue(nodeTwin, pQueueTwin);
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return totalMoves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        
        Stack<Board> solution = new Stack<Board>();
        Node current = last;
        
        while (current != null) {
            solution.push(current.board);
            current = current.previous;
        }
        
        return solution;
    }
    
    /* Private Members */
    private class Node implements Comparable<Node> {
        private Board board; 
        private Node previous; // previous node
        private int moves; // no of moves to reach this board
        private int priority; // priority of this board
        
        public Node(Board b, int m, Node p) {
            board = b;
            previous = p;
            moves = m;
            priority = b.manhattan() + moves;
        }
        
        public int compareTo(Node that) {
            if (this.priority < that.priority) {
                return -1;
            } else if (this.priority > that.priority) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
    private void enqueue(Node node, MinPQ<Node> pQueue) {
        Iterable<Board> neighbors = node.board.neighbors();
        
        for (Board next: neighbors) {
            if (node.previous == null || !next.equals(node.previous.board)) {
                pQueue.insert(new Node(next, node.moves + 1, node));
            }
        }
    }
}