import java.util.Scanner;

/**
 * Jeremy Carney, CSUGlobal, CSC400, Dr. Angelo Luo, July 6th, 2025
 * Represents an instructor with first name, last name, and number of courses taught.
 */
class Instructor {
    private final String firstName;
    private final String lastName;
    private final int coursesTaught;

    /**
     * Constructs an Instructor with specified details.
     * @param firstName Instructor's first name
     * @param lastName Instructor's last name
     * @param coursesTaught Number of courses taught
     */
    public Instructor(String firstName, String lastName, int coursesTaught) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesTaught = coursesTaught;
    }

    /**
     * Gets the instructor's last name.
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the number of courses taught.
     * @return Number of courses
     */
    public int getCoursesTaught() {
        return coursesTaught;
    }

    /**
     * Returns string representation of the instructor.
     * @return Formatted string of instructor details
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + ", Courses: " + coursesTaught;
    }
}

/**
 * Implements a queue to store Instructor objects with sorting capabilities.
 */
class InstructorQueue {
    private final Instructor[] queue;
    private int size;
    private static final int MAX_SIZE = 10;

    /**
     * Constructs an empty queue.
     */
    public InstructorQueue() {
        queue = new Instructor[MAX_SIZE];
        size = 0;
    }

    /**
     * Adds an instructor to the queue.
     * @param instructor Instructor to add
     * @return True if added successfully, false if queue is full
     */
    public boolean addInstructor(Instructor instructor) {
        if (size >= MAX_SIZE) {
            return false;
        }
        queue[size] = instructor;
        size++;
        return true;
    }

    /**
     * Gets the current size of the queue.
     * @return Number of instructors in queue
     */
    public int getSize() {
        return size;
    }

    /**
     * Sorts queue by last name in descending order using quicksort.
     */
    public void sortByLastName() {
        quickSortLastName(0, size - 1);
    }

    /**
     * Sorts queue by courses taught in descending order using quicksort.
     */
    public void sortByCourses() {
        quickSortCourses(0, size - 1);
    }

    /**
     * Implements quicksort for last name sorting.
     * @param low Starting index
     * @param high Ending index
     */
    private void quickSortLastName(int low, int high) {
        if (low < high) {
            int pi = partitionLastName(low, high);
            quickSortLastName(low, pi - 1);
            quickSortLastName(pi + 1, high);
        }
    }

    /**
     * Partitions queue for last name sorting.
     * @param low Starting index
     * @param high Ending index
     * @return Partition index
     */
    private int partitionLastName(int low, int high) {
        String pivot = queue[high].getLastName();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (queue[j].getLastName().compareTo(pivot) > 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Implements quicksort for courses taught sorting.
     * @param low Starting index
     * @param high Ending index
     */
    private void quickSortCourses(int low, int high) {
        if (low < high) {
            int pi = partitionCourses(low, high);
            quickSortCourses(low, pi - 1);
            quickSortCourses(pi + 1, high);
        }
    }

    /**
     * Partitions queue for courses taught sorting.
     * @param low Starting index
     * @param high Ending index
     * @return Partition index
     */
    private int partitionCourses(int low, int high) {
        int pivot = queue[high].getCoursesTaught();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (queue[j].getCoursesTaught() > pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Swaps two elements in the queue.
     * @param i Index of first element
     * @param j Index of second element
     */
    private void swap(int i, int j) {
        Instructor temp = queue[i];
        queue[i] = queue[j];
        queue[j] = temp;
    }

    /**
     * Prints the contents of the queue.
     */
    public void printQueue() {
        for (int i = 0; i < size; i++) {
            System.out.println(queue[i]);
        }
    }
}

/**
 * Main program to manage instructor queue and sorting.
 */
public class InstructorQueueProgram {
    /**
     * Main method to run the program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            InstructorQueue queue = new InstructorQueue();

            // Prompt user to add five instructors
            for (int i = 0; i < 5; i++) {
                System.out.println("Enter details for instructor " + (i + 1) + ":");
                System.out.print("First Name: ");
                String firstName = scanner.nextLine();
                System.out.print("Last Name: ");
                String lastName = scanner.nextLine();
                System.out.print("Number of Courses: ");
                int courses = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                Instructor instructor = new Instructor(firstName, lastName, courses);
                queue.addInstructor(instructor);
            }

            // Print original queue
            System.out.println("\nOriginal Queue:");
            queue.printQueue();

            // Sort and print by last name
            queue.sortByLastName();
            System.out.println("\nSorted by Last Name (Descending):");
            queue.printQueue();

            // Sort and print by courses taught
            queue.sortByCourses();
            System.out.println("\nSorted by Courses Taught (Descending):");
            queue.printQueue();
        }
    }
}