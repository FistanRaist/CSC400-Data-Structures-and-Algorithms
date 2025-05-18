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
        Bag<String> bag = new Bag<>();
        
        // Add elements, including duplicates
        bag.add("Fighter's Honed Broadsword");
        bag.add("Cleric's Blessed Amulet");
        bag.add("Fighter's Honed Broadsword");
        bag.add("Mage's Arcane Orb");
        bag.add("Thief's Shadow Key");
        bag.add("Cleric's Blessed Amulet");
        
        // Print initial bag contents
        System.out.println("Initial bag: " + bag);
        
        // Test contains method
        System.out.println("Contains Fighter's Honed Broadsword? " + bag.contains("Fighter's Honed Broadsword"));
        System.out.println("Contains Goblin Skull? " + bag.contains("Goblin Skull"));
        
        // Test count method
        System.out.println("Count of Fighter's Honed Broadsword: " + bag.count("Fighter's Honed Broadsword"));
        System.out.println("Count of Cleric's Blessed Amulet: " + bag.count("Cleric's Blessed Amulet"));
        
        // Remove an element
        bag.remove("Fighter's Honed Broadsword");
        System.out.println("After removing one Fighter's Honed Broadsword: " + bag);
        
        // Test contains and count after removal
        System.out.println("Contains Fighter's Honed Broadsword? " + bag.contains("Fighter's Honed Broadsword"));
        System.out.println("Count of Fighter's Honed Broadsword: " + bag.count("Fighter's Honed Broadsword"));
    }
}