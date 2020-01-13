/* *****************************************************************************
 *  Name: Neeko
 *  Date: Jan 12, 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        N = 0;
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // adjust the size of queue as specified
    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        // copy elements in the old queue to the resized queue
        for (int i = 0; i < N; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // enlarge the size of queue when it is full
        if (N == queue.length) resize(2 * queue.length);
        queue[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue underflow");
        int randomNum = StdRandom.uniform(N);
        Item randomItem = queue[randomNum];

        if (randomNum != N-1) queue[randomNum] = queue[N-1];
        queue[N-1] = null;
        N--;

        // shrink the size of queue to 1/2 long when it reaches 1/4 of the size
        if (N > 0 && N == queue.length / 4) resize(queue.length / 2);

        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue underflow");

        int randomNum = StdRandom.uniform(N);
        return queue[randomNum];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private Item[] randomQueue;
        private int current;

        public RandomIterator() {
            randomQueue = (Item[]) new Object[N];
            current = 0;

            for (int k = 0; k < N; k++) {
                randomQueue[k] = queue[k];
            }
            StdRandom.shuffle(randomQueue);
        }

        public boolean hasNext() {
            return current < N;
        }

        public Item next() {
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = randomQueue[current];
            current++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        System.out.println("empty: " + randomizedQueue.isEmpty());
        System.out.println("size before adding 4 elements: " + randomizedQueue.size());
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(8);
        randomizedQueue.enqueue(10);
        System.out.println("sample: " + randomizedQueue.sample());
        System.out.println("dequeue: " + randomizedQueue.dequeue());
        System.out.println("size after dequeuing 1 element: " + randomizedQueue.size());

        for (Integer integer: randomizedQueue) {
            System.out.print(integer + " ");
        }
    }

}
