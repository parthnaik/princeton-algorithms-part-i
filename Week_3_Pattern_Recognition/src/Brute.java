import java.util.Arrays;

public class Brute {
    private static void findCollinearPoints(Point[] points) {
        int N = points.length;
        Arrays.sort(points);
        
        for (int i = 0; i < N; i++) {
            points[i].draw(); // draw all points
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    double slopeij = points[i].slopeTo(points[j]);
                    double slopejk = points[j].slopeTo(points[k]);
                    
                    if (slopeij == slopejk) {
                        for (int l = k + 1; l < N; l++) {
                            double slopekl = points[k].slopeTo(points[l]);
                            
                            if (slopejk == slopekl) {
                                // points are collinear if in this loop
                                String p = points[i].toString();
                                String q = points[j].toString();
                                String r = points[k].toString();
                                String s = points[l].toString(); 
                                
                                StdOut.println(p + " -> " + q + " -> " + r + " -> " + s);
                                drawLine(points[i], points[j], points[k], points[l]);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void drawLine(Point p, Point q, Point r, Point s) {
        Point[] linePoints = { p, q, r , s };
        
        // draw line connecting points
        linePoints[0].drawTo(linePoints[3]);
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please provide a valid filename as argument.");
        }
        
        In inputFile = new In(args[0]);
        int N = inputFile.readInt(); // number of points in file
        
        Point[] points = new Point[N];
        
        // Rescale for visualization
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        for (int i = 0; !inputFile.isEmpty(); i++) {
            int x = inputFile.readInt();
            int y = inputFile.readInt();
            
            points[i] = new Point(x, y);
        }
        
        Brute.findCollinearPoints(points);
    }
}
