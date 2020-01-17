/* *****************************************************************************
 *  Name: Neeko
 *  Date: Jan 12, 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        // Get the input from command line
        int number = Integer.parseInt(args[0]);
        RandomizedQueue<String> rqueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rqueue.enqueue(StdIn.readString());
        }

        while (number > 0) {
            System.out.println(rqueue.dequeue());
            number--;
        }

    }
}
