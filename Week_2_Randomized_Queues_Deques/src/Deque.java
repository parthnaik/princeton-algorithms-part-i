import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // Private Members
    private int N; // size of deque
    private Node<Item> first; // first item of deque
    private Node<Item> last; // last item of deque
    
    // Inner Node Class
    private static class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;
    }
    
    // Construct an empty Deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }
    
    // Check if Deque is empty
    public boolean isEmpty()  {
        return N == 0;
    }
    
    // Return the number of items on the Deque
    public int size() {
        return N;
    }
    
    // Add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("Cannot add a null item");
        }
        
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        
        if (isEmpty()) {
            last = first;     
        } else {
            oldfirst.previous = first;
        }
        
        N++;
    }
    
    // Add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("Cannot add a null item");
        }
        
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        
        N++;
    }
    
    // Remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }
        
        Node<Item> oldfirst = first;
        first = first.next;
        
        // if only 1 node is left at time of remove
        if (N == 1) {
            // set last to null as well
            last = null;
        } else {
            first.previous = null;
        }
        
        N--;
        return oldfirst.item;
    }
    
    // Remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }
        
        Node<Item> oldlast = last;
        last = last.previous;
        
        // if only 1 node is left at time of remove
        if (N == 1) {
            // set first to null as well
            first = null;
        } else {
            last.next = null;
        }
        
        N--;
        return oldlast.item;
    }
    
    // Returns an iterator to that deque from front to end
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }
    
    // Class that implements the iterator
    private class ListIterator implements Iterator<Item> {
        private Node<Item> current;
        
        public ListIterator(Node<Item> first) {
            current = first;
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    // Return an iterator over items in order from front to end
    // public Iterator<Item> iterator()         
    
    // Unit testing
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(0);
        StdOut.println(d.removeLast());
        d.addFirst(2);
        StdOut.println(d.removeFirst());
        d.addLast(4);
    }
}
