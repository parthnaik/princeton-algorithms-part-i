public class KdTree {
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        KdTree set = new KdTree();
        set.insert(new Point2D(0.206107, 0.095492));
        set.insert(new Point2D(0.975528, 0.654508));
        set.insert(new Point2D(0.024472, 0.345492));
        set.insert(new Point2D(0.793893, 0.095492));
        set.insert(new Point2D(0.793893, 0.904508));
        set.insert(new Point2D(0.975528, 0.345492));
        set.insert(new Point2D(0.206107, 0.904508));
        set.insert(new Point2D(0.500000, 0.000000));
        set.insert(new Point2D(0.024472, 0.654508));
        set.insert(new Point2D(0.500000, 1.000000));
        
        set.draw();
    }
    
    // construct an empty set of points 
    public KdTree() {
        size = 0;
        root = null;
    }
   
    // is the set empty? 
    public boolean isEmpty() {
        return size == 0;
    }    
   
    // number of points in the set 
    public int size() {
        return size;
    }
    
//    public void insert(Point2D p) {
//        RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0); // rectangle for root
//        root = insert(root, p, r, VERTICAL);
//    }
    public void insert(Point2D p) {
        root = insert(root, p, 0.0, 0.0, 1.0, 1.0, VERTICAL);
    }
   
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        return contains(root, p);
    }
   
    // draw all points to standard draw 
    public void draw()  {
        draw(root);
    }
   
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> pointsInRange = new Stack<Point2D>();
        range(root, rect, pointsInRange);
        
        return pointsInRange;
    }
   
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        Point2D c = root.point; // start searching at root
        
        return nearest(root, p, c);
    }
    
    // Instance Variables
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private int size; // number of points in tree
    private Node root; // root of tree
    
    // Inner Class
    private class Node {
        private Node leftbottom; // left/bottom subtree rectangle
        private Node righttop; // right/top subtree rectangle
        private Point2D point; // point for node
        private RectHV rect; // rectangle surrounding node
        private boolean type; // horizontal or vertical
        
        public Node(Point2D pt, RectHV r, boolean t) {
            point = pt;
            rect = r;
            type = t;
        }
        
        // check if point in root and pt are equal
        public boolean isEqual(Point2D pt) {
            if (point.x() == pt.x() && point.y() == pt.y()) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    // Private Methods    
    // insert node into subtree rooted at root
    private Node insert(Node root, Point2D p, double x0, double y0, double x1, double y1, boolean type) {
        if (root == null) {
            // if subtree root is empty, insert node
            size++;
            RectHV r = new RectHV(x0, y0, x1, y1);
            return new Node(p, r, type);
        } else if (root.isEqual(p)) {
            // if point equal to root, return root node
            return root;
        }
        
        // The current node is vertical: compare x-coordinates
        if (type == VERTICAL) {
            // if VERTICAL compare x-coords
            if (p.x() < root.point.x()) {
                root.leftbottom = insert(root.leftbottom, p, x0, y0, root.point.x(), y1, HORIZONTAL);
            } else {
                root.righttop = insert(root.righttop, p, root.point.x(), y0, x1, y1, HORIZONTAL);
            }
        } else {
            // if HORIZONTAL compare y-coords
            if (p.y() < root.point.y()) {
                root.leftbottom = insert(root.leftbottom, p, x0, y0, x1, root.point.y(), VERTICAL);
            } else {
                root.righttop = insert(root.righttop, p, x0, root.point.y(), x1, y1, VERTICAL);
            }
        }
        
        return root;
    }
    
    // check if subtree rooted at root contains point
    private boolean contains(Node root, Point2D p) {
        // was not found
        if (root == null) return false;
        // was found
        if (root.isEqual(p)) return true;
        
        if (root.type == VERTICAL) {
            if (p.x() < root.point.x()) {
                return contains(root.leftbottom, p);
            } else {
                return contains(root.righttop, p);
            }
        } else {
            if (p.y() < root.point.y()) {
                return contains(root.leftbottom, p);
            } else {
                return contains(root.righttop, p);
            }
        }
    }
    
    // draw points for tree subrooted at root
    private void draw(Node root) {
        if (root == null) return;
        
        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        root.point.draw();
        
        Point2D lineStart, lineEnd;
        if (root.type == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            lineStart = new Point2D(root.point.x(), root.rect.ymin());
            lineEnd = new Point2D(root.point.x(), root.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            lineStart = new Point2D(root.rect.xmin(), root.point.y());
            lineEnd = new Point2D(root.rect.xmax(), root.point.y());
        }
        
        StdDraw.setPenRadius();
        lineStart.drawTo(lineEnd);
        
        // draw children recursively
        draw(root.leftbottom);
        draw(root.righttop);
    }
    
    // find range of points for tree subrooted at root
    private void range(Node root, RectHV rect, Stack<Point2D> pointsInRange) {
        if (root == null) return;
        if (!rect.intersects(root.rect)) return;
        
        // if current point is in rectangle, push point onto stack
        if (rect.contains(root.point)) {
            pointsInRange.push(root.point);
        }
        
        range(root.leftbottom, rect, pointsInRange);
        range(root.righttop, rect, pointsInRange);
    }
    
    // find nearest point for tree subrooted at root
    private Point2D nearest(Node root, Point2D p, Point2D c) {
        Point2D closest = c;
        
        // if there are no more nodes, do closest found so far
        if (root == null) return closest;
        
        // update closest point
        if (root.point.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
            closest = root.point;
        }
        
        // if current rectangle is closer to p than the closest point, check its subtrees
        if (root.rect.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
            Node near;
            Node far;
            
            if ((root.type == VERTICAL && p.x() < root.point.x()) || (root.type == HORIZONTAL && p.y() < root.point.y())) {
                near = root.leftbottom;
                far = root.righttop;
            } else {
                near = root.righttop;
                far = root.leftbottom;
            }
            
            // Check subtree on same side as p
            closest = nearest(near, p, closest);
            // Check subtree on opposite side as p
            closest = nearest(far, p, closest);
        }
        
        return closest;
    }
}