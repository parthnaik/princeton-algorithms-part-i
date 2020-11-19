/*************************************************************************
 * Name: Parth Naik
 * Email: parthnaik1991@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate
    
    private class BySlope implements Comparator<Point> {
        public int compare(Point v, Point w) {
            double slopeV = slopeTo(v);
            double slopeW = slopeTo(w);
            
            return Double.compare(slopeV, slopeW);
        }
    }

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        // Degenerate Line Segments
        if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        }
        
        double dy = that.y - this.y;
        double dx = that.x - this.x;
        
        // Horizontal Line
        if (dy == 0.0) {
            return +0.0;
        }
        
        // Vertical Line
        if (dx == 0.0) {
            return Double.POSITIVE_INFINITY;
        }
        
        return dy / dx;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int dy = this.y - that.y;
        
        if (dy != 0) {
            return dy;
        }
        
        int dx = this.x - that.x;
        
        return dx;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}