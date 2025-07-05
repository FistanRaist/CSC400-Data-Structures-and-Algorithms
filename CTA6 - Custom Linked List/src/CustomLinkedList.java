import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A custom implementation of a singly linked list with iterator support.
 * Provides basic operations for insertion, deletion, and traversal.
 * @author [Your Name]
 */
public class CustomLinkedList {
    private Node head;

    /**
     * Custom exception for file processing errors.
     */
    private static class FileProcessingException extends Exception {
        /**
         * Constructs a FileProcessingException with the specified message.
         * @param message The error message
         */
        FileProcessingException(String message) {
            super(message);
        }
    }

    /**
     * Node class representing an element in the linked list.
     */
    private class Node {
        int data;
        Node next;

        /**
         * Constructs a new Node with given data.
         * @param data The integer value to store
         */
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Inserts a new node with the specified data at the end of the list.
     * @param data The integer value to insert
     */
    public void insert(int data) {
        Node newNode = createNode(data);
        if (isEmpty()) {
            setHead(newNode);
        } else {
            appendNode(newNode);
        }
    }

    /**
     * Creates a new node with the given data.
     * @param data The integer value for the node
     * @return The newly created node
     */
    private Node createNode(int data) {
        return new Node(data);
    }

    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise
     */
    private boolean isEmpty() {
        return head == null;
    }

    /**
     * Sets the head of the list to the specified node.
     * @param node The node to set as head
     */
    private void setHead(Node node) {
        head = node;
    }

    /**
     * Appends a node to the end of the list.
     * @param node The node to append
     */
    private void appendNode(Node node) {
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = node;
    }

    /**
     * Deletes the first occurrence of a node with the specified data.
     * @param data The integer value to delete
     * @return true if deletion was successful, false if data was not found
     */
    public boolean delete(int data) {
        if (isEmpty()) return false;
        if (isHeadData(data)) {
            deleteHead();
            return true;
        }
        return deleteNonHead(data);
    }

    /**
     * Checks if the head node contains the specified data.
     * @param data The integer value to check
     * @return true if head contains the data, false otherwise
     */
    private boolean isHeadData(int data) {
        return head.data == data;
    }

    /**
     * Deletes the head node.
     */
    private void deleteHead() {
        head = head.next;
    }

    /**
     * Deletes the first non-head node with the specified data.
     * @param data The integer value to delete
     * @return true if deletion was successful, false if data was not found
     */
    private boolean deleteNonHead(int data) {
        Node current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
            return true;
        }
        return false;
    }

    /**
     * Returns an iterator for traversing the linked list.
     * @return An iterator for the linked list
     */
    public Iterator<Integer> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Reads integers from a file and inserts them into the list.
     * @param filename The name of the file to read
     * @throws FileProcessingException If an error occurs while reading the file
     */
    public void readFromFile(String filename) throws FileProcessingException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/" + filename))) {
            processFileLines(br);
        } catch (IOException e) {
            throw new FileProcessingException("Failed to read file " + filename + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new FileProcessingException("Invalid integer format in file " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Processes lines from a BufferedReader and inserts integers.
     * @param br The BufferedReader to process
     * @throws IOException If an I/O error occurs
     * @throws NumberFormatException If a line cannot be parsed as an integer
     */
    private void processFileLines(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            insert(Integer.parseInt(line.trim()));
        }
    }

    /**
     * Iterator class for traversing the linked list.
     */
    private class LinkedListIterator implements Iterator<Integer> {
        private Node current = head;

        /**
         * Checks if there is a next element in the iteration.
         * @return true if there is a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element and advances the iterator.
         * @return The next integer in the list
         * @throws NoSuchElementException If no next element exists
         */
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return advanceIterator();
        }

        /**
         * Advances the iterator and returns the current data.
         * @return The current node's data
         */
        private Integer advanceIterator() {
            int data = current.data;
            current = current.next;
            return data;
        }
    }

    /**
     * Demonstrates the functionality of the CustomLinkedList.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        CustomLinkedList linkedList = new CustomLinkedList();
        
        // Read from file
        try {
            linkedList.readFromFile("input.txt");
        } catch (FileProcessingException e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }
        
        // Insert elements
        linkedList.insert(1);
        linkedList.insert(2);
        linkedList.insert(3);
        
        // Display elements
        printList(linkedList);
        
        // Delete element
        boolean deleted = linkedList.delete(2);
        if (!deleted) {
            System.out.println("Element 2 not found in the list.");
        }
        
        // Display after deletion
        printList(linkedList);
    }

    /**
     * Prints all elements in the linked list using iterator.
     * @param list The CustomLinkedList to print
     */
    private static void printList(CustomLinkedList list) {
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }
}