// Bag class implementing a generic multiset data structure
import java.util.HashMap;
import java.util.Map;

public class Bag<T> {
    // HashMap to store elements and their counts
    private final Map<T, Integer> items;

    // Constructor initializes empty bag
    public Bag() {
        items = new HashMap<>();
    }

    // Adds an item to the bag
    public void add(T item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    // Removes one occurrence of an item from the bag
    public void remove(T item) {
        if (items.containsKey(item)) {
            int count = items.get(item);
            if (count == 1) {
                items.remove(item);
            } else {
                items.put(item, count - 1);
            }
        }
    }

    // Checks if an item exists in the bag
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    // Returns the count of an item in the bag
    public int count(T item) {
        return items.getOrDefault(item, 0);
    }

    // Returns the total number of elements in the bag, including duplicates
    public int size() {
        int total = 0;
        for (int count : items.values()) {
            total += count;
        }
        return total;
    }

    // Merges another bag into this bag
    public void merge(Bag<T> otherBag) {
        for (Map.Entry<T, Integer> entry : otherBag.items.entrySet()) {
            T item = entry.getKey();
            int count = entry.getValue();
            items.put(item, items.getOrDefault(item, 0) + count);
        }
    }

    // Returns a new bag with only distinct elements
    public Bag<T> distinct() {
        Bag<T> distinctBag = new Bag<>();
        for (T item : items.keySet()) {
            distinctBag.add(item);
        }
        return distinctBag;
    }

    // Returns string representation of bag contents
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Bag{");
        for (Map.Entry<T, Integer> entry : items.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
        }
        if (!items.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove trailing comma
        }
        sb.append("}");
        return sb.toString();
    }

    // Main method to demonstrate Bag functionality
    public static void main(String[] args) {
        // Create two bags
        Bag<String> bag1 = new Bag<>();
        Bag<String> bag2 = new Bag<>();

        // Add elements to bag1
        bag1.add("Fighter’s Honed Broadsword");
        bag1.add("Cleric’s Blessed Amulet");
        bag1.add("Fighter’s Honed Broadsword");
        bag1.add("Mage’s Arcane Orb");

        // Add elements to bag2
        bag2.add("Cleric’s Blessed Amulet");
        bag2.add("Thief’s Shadow Key");
        bag2.add("Fighter’s Honed Broadsword");
        bag2.add("Cleric’s Blessed Amulet");

        // Print size of each bag
        System.out.println("Bag1 size: " + bag1.size());
        System.out.println("Bag2 size: " + bag2.size());

        // Merge bag2 into bag1
        bag1.merge(bag2);
        System.out.println("Merged bag1: " + bag1);

        // Create and print distinct bag
        Bag<String> distinctBag = bag1.distinct();
        System.out.println("Distinct bag: " + distinctBag);
    }
}