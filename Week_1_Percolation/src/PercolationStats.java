public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Both N and T have to be greater than 0.");
        }
        
        size = N;
        numRuns = T;
        percolationThresholds = new double[T];
        
        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(N);
            percolationThresholds[i] = findThreshold(perc);
        }
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(numRuns);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(numRuns);
    }
    
    // Private Methods
    private double findThreshold(Percolation perc) {   
        int openedSites = 0;
        int totalSites = size * size;
        double threshold;
        
        while (!perc.percolates()) {
            int row = StdRandom.uniform(size) + 1;
            int col = StdRandom.uniform(size) + 1;
            
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
                openedSites++;
            }
        }
        
        threshold = (double) openedSites / totalSites;
        
        return threshold;
    }
    
    // Private Members
    private int size;
    private int numRuns;
    private double percolationThresholds[];
    
    // test client
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new java.lang.IllegalArgumentException("Invalid args. The correct format is 'java PercolationStats int1 int2'");
        }
        
        Integer N = Integer.parseInt(args[0]);
        Integer T = Integer.parseInt(args[1]);
        
        PercolationStats percStats = new PercolationStats(N, T);
        
        double mean = percStats.mean();
        double stddev = percStats.stddev();
        double confidenceLo = percStats.confidenceLo();
        double confidenceHi = percStats.confidenceHi();
        
        StdOut.println("mean                    = " + Double.toString(mean));
        StdOut.println("stddev                  = " + Double.toString(stddev));
        StdOut.println("95% confidence interval = " + Double.toString(confidenceLo) + ", " + Double.toString(confidenceHi));
    }
}
