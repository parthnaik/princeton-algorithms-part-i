import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayStack<Item> implements Iterable<Item> {
    private Item[] a;
    private int N; // number of elements on stack or the next open index in array
    
    // Constructor
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        a = (Item []) new Object[2];
    }
    
    // checks if stack is empty
    public boolean isEmpty() {
        return N == 0;
    }
    
    // returns the size of the stack
    public int size() {
        return N;
    }
    
    // push an item on to the stack
    public void push(Item item) {
        if (N == a.length) resize(2 * a.length);
        a[N++] = item;
    }
    
    // pop item from the stack
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[N - 1];
        a[N - 1] = null; // to avoid loitering
        N--;
        
        // shrink array if necessary
        if (N == a.length / 4) resize (a.length / 2);
        return item;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        for (int i = 0; i < N; i++) {
            s.append(a[i] + " ");
        }
        
        return s.toString();
    }
    
    // resize the array to new capacity
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] resizedStack = (Item[])new Object[capacity];
        
        for (int i = 0; i < N; i++) {
            resizedStack[i] = a[i];
        }
        
        a = resizedStack;
    }
    
    // returns an iterator which can iterates in LIFO order
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }
    
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;
        
        public ReverseArrayIterator() {
            i = N - 1;
        }
        
        public boolean hasNext() {
            return i >= 0;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }
    
    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        stack.push(2);
        stack.push(2);
        StdOut.println(stack.toString());
        stack.pop();
        StdOut.println(stack.toString());
    }
}
