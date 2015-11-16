import java.io.IOException;
import java.io.FileInputStream;
import java.net.URL;

public class Subset {
    public static void main(String[] args) {  
        URL url = Subset.class.getResource("subset_stdin.txt");
        
        try {
            System.setIn(new FileInputStream(url.getPath()));
        } catch (IOException e) {
            StdOut.println("Could not read file.");
        }
        
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int k; // command line integer
        
        // check if number of arguments are correct
        if (args.length != 1) {
            StdOut.println("Please provide one valid integer argument.");
            return;
        }
        
        // check that the format of the argument is correct
        try {
            k = Integer.parseInt(args[0]);
        } catch (NumberFormatException n) {
            throw new IllegalArgumentException(n);
        }
        
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }
        
        if (k > q.size()) {
            StdOut.println("The number of tokens requested k is greater than total number of tokens.");
            return;
        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
