import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class Fast {
    private static void findCollinearPoints(Point[] points) {
        int N = points.length;
        Point[] pointsBySlope = new Point[N];
        pointsBySlope = Arrays.copyOf(points, N);
        ArrayList<Point> collinearPoints = new ArrayList<Point>(); 
        
        for (int i = 0; i < N; i++) {
            Point origin = points[i];
            origin.draw(); // draw all points
            Arrays.sort(pointsBySlope, origin.SLOPE_ORDER);
            
            // upto (N - 3) because we want a minimum of 4 collinear points incl origin
            int j = 0;
            
            while (j <= N - 3) {
                // skip if point is equal to origin
                if (pointsBySlope[j].toString().equals(origin.toString())) {
                    j++;
                }
                
                // add first point
                collinearPoints.add(pointsBySlope[j]);
                double referenceSlope = pointsBySlope[j].slopeTo(origin);
                
                // keep adding points if they make the same slope with origin as the first point
                while (pointsBySlope[++j].slopeTo(origin) == referenceSlope) {
                    collinearPoints.add(pointsBySlope[j]);
                    // break if j + 1 is out of bounds
                    if (!(j + 1 < N)) break;
                }
                
                if (collinearPoints.size() >= 3) {
                    // sorting to make next if loop possible
                    Collections.sort(collinearPoints);
                    
                    // IMPORTANT
                    // only print when origin is the smallest to avoid permutations
                    if (origin.compareTo(collinearPoints.get(0)) < 0) {
                        int lastPoint = collinearPoints.size() - 1;
                        
                        // print origin and the 3 or more collinear points
                        StdOut.print(origin + " -> ");
                        
                        for (int k = 0; k < collinearPoints.size(); k++) {
                            if (k == lastPoint) { 
                                StdOut.print(collinearPoints.get(k).toString());
                                break;
                            }
                            
                            StdOut.print(collinearPoints.get(k).toString() + " -> ");
                        }
                      
                        StdOut.println();
                        
                        // since the list is sorted, we can draw line from origin to last point
                        origin.drawTo(collinearPoints.get(lastPoint));
                    }
                }
                
                // delete all elements in the list
                collinearPoints.clear();
            }
        }
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please provide a valid filename as the only argument.");
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
        
        Fast.findCollinearPoints(points);
    }
}
