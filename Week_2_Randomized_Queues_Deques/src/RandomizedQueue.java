import java.util.NoSuchElementException;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {
    // Private Members
    private int N; // number of elements in the queue and next available slot in queue
    private Item[] q; // queue array
    
    // Constructor for randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        N = 0;
    }
    
    // Check if queue is empty
    public boolean isEmpty() {
        return N == 0;
    }
    
    // Return the number of items on the queue
    public int size() {
        return N;
    }
    
    // Add an item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Cannot add a null item");
        if (N == q.length) resize(q.length * 2); // double the size of array if capacity is reached
        q[N++] = item; // add item to end of queue
    }
    
    // Remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        if (N == q.length / 4) resize(q.length / 2);
        int random = StdRandom.uniform(N); // random index for elements in the queue
        Item randomItem = q[random]; // store random item temporarily
        q[random] = q[N - 1]; // copy value of last element to the place of removed element
        q[N - 1] = null; // to avoid loitering
        N--;
        
        return randomItem;
    }
    
    // Return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int random = StdRandom.uniform(N); // random index for elements in queue
        
        return q[random];
    }
    
    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] shuffledIndexes;
        private int i;
        
        public RandomizedQueueIterator() {
            i = 0;
            shuffledIndexes = new int[N];
            
            for (int i = 0; i < N; i++) {
                shuffledIndexes[i] = i;
            }
            
            StdRandom.shuffle(shuffledIndexes); // shuffle indexes of elements in the queue
        }
        
        public boolean hasNext() {
            return i < shuffledIndexes.length; 
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int random = shuffledIndexes[i++];
            return q[random];
        }
    }
    
    // Resize the array
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] newq = (Item[]) new Object[capacity];
        
        for (int i = 0; i < N; i++) {
            newq[i] = q[i];
        }
        
        q = newq;
    }
    
    // Unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(0);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        
        StdOut.print(q.dequeue() + " ");
        StdOut.print(q.dequeue() + " ");
        StdOut.print(q.dequeue() + " ");
        StdOut.println();
        
        Iterator<Integer> i = q.iterator();
        
        while (i.hasNext()) {
            StdOut.print(i.next() + " ");
        }
    }
}
