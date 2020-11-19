public class PointSET {
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        
    }
    
    // Instance variables
    private SET<Point2D> pointSet;
    
    // construct an empty set of points 
    public PointSET() {
        pointSet = new SET<Point2D>();
    }
   
    // is the set empty? 
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }    
   
    // number of points in the set 
    public int size() {
        return pointSet.size();
    }
   
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pointSet.add(p);
    }
   
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }
   
    // draw all points to standard draw 
    public void draw()  {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        
        for (Point2D pt : pointSet) {
            pt.draw();
        }
    }
   
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.NullPointerException("rect cannot be null.");
        Stack<Point2D> rectPoints = new Stack<Point2D>();
        
        for (Point2D pt : pointSet) {
            if (rect.contains(pt)) {
                rectPoints.push(pt);
            }
        }
            
        return rectPoints;
    }
   
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (pointSet.isEmpty()) return null;
        Point2D nearestNeighbor = null;
        Double distance = Double.POSITIVE_INFINITY;
        
        for (Point2D pt : pointSet) {
            if (p.distanceSquaredTo(pt) < distance) {
                nearestNeighbor = pt;
                distance = p.distanceSquaredTo(pt);
            }
        }
        
        return nearestNeighbor;
    }
}