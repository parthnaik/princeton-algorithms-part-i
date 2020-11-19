import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListStack<Item> implements Iterable<Item> {
    private int N; // size of stack
    private Node<Item> first; // top of stack
    
    // Inner Node Class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }
    
    // Constructor
    public LinkedListStack() {
        first = null;
        N = 0;
    }
    
    // Push item on to the stack
    public void push(Item item) {
        Node<Item> newnode = new Node<Item>();
        newnode.item = item;
        newnode.next = first;
        first = newnode;
        N++;
    }
    
    // Pop item from the stack
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Node<Item> oldfirst = first;
        first = first.next;
        N--;
        return oldfirst.item;
    }
    
    // Returns the size of the stack
    public int size() {
        return N;
    }
    
    // Checks if stack is empty
    public boolean isEmpty() {
        return N == 0;
    }
    
    // Array in string format
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        for (Item item : this) {
            s.append(item + " ");
        }
        
        return s.toString();
    }
    
    // Returns an iterator to that stack that iterates in LIFO order.
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
    
    public static void main (String [] args) {        
        LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
        stack.push(0);
        StdOut.print(stack.pop() + " ");
        stack.push(1);
        stack.push(2);
        StdOut.print(stack.pop() + " ");
        stack.push(3);
        stack.push(4);
        StdOut.print(stack.pop() + " ");
        StdOut.print(stack.pop() + " ");
    }
}
