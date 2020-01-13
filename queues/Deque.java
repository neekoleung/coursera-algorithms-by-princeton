/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node head;
    private Node tail;

    private class Node {
        private Item node;
        private Node last;
        private Node next;
    }

    // construct an empty deque
    public Deque() {
        N = 0;
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node temp = head;    // Use a temp to save the elder first item
        head = new Node();    // Refresh the old first to the new one
        head.node = item;    // Link the new first to the old first
        head.next = temp;
        if (isEmpty())   // If the queue is empty, the added first is also the last
            tail = head;
        else    // Link the old first to the new first
            temp.last = head;
        N++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node temp = tail;
        tail = new Node();
        tail.node = item;
        tail.last = temp;
        if (isEmpty())
            head = tail;
        else
            temp.next = tail;
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue underflow");
        Item oldfirst = head.node;   // Use a temp to save the elder first item to return
        head = head.next;  // Postpone the pointer of the first
        N--;
        if (isEmpty())
            tail = null;    // If the queue is empty, the last one should be null
        else
            head.last = null;   // Update the last one's last pointer
        return oldfirst;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue underflow");
        Item oldlast = tail.node;
        tail = tail.last;
        N--;
        if (isEmpty())
            head = null;
        else
            tail.next = null;
        return oldlast;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new myIterator();
    }

    private class myIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.node;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        deque.addFirst(3);
        deque.addLast(5);
        deque.addLast(6);
        deque.addFirst(2);
        System.out.println("size after adding:" + deque.size());
        Iterator<Integer> element = deque.iterator();
        while (element.hasNext()) {
            System.out.print(element.next() + " ");
        }
    }
}
