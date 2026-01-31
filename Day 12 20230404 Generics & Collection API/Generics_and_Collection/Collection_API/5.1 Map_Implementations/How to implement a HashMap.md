# HOW TO IMPLEMENT YOUR OWN HASHMAP

## 1. THE BASIC CONCEPT
A HashMap stores key-value pairs. It uses "Hashing" to compute an index into an array of "buckets".

- **Buckets**: An array of Node objects.
- **Node**: An object containing Key, Value, Next Node pointer, and optionally Hash.
- **Collision Handling**: If two keys hash to the same index (bucket), they are stored in a Linked List (Separate Chaining).

## 2. KEY COMPONENTS
- **Inner Node Class**: `class Node<K,V> { K key; V value; Node<K,V> next; }`
- **Bucket Array**: `Node<K,V>[] buckets;`
- **put(K key, V value)**:
    1. Calculate Hash of key.
    2. Convert Hash to Index: `index = hash % capacity`.
    3. If bucket empty -> Add new Node.
    4. If bucket occupied -> Traverse list. If key exists, update value. Else, append to end.
- **get(K key)**:
    1. Calculate Hash -> Index.
    2. Traverse list at that index.
    3. Return value if key matches.

## 3. IMPLEMENTATION EXAMPLE (MyHashMap)

```java
public class MyHashMap<K, V> {
    
    // Internal Node class (Like a LinkedList Node)
    class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Node<K, V>[] buckets;
    private static final int DEFAULT_CAPACITY = 16;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = new Node[DEFAULT_CAPACITY];
    }

    // PUT Method
    public void put(K key, V value) {
        int index = getBucketIndex(key);
        Node<K, V> head = buckets[index];

        // Check if key already exists in the chain
        Node<K, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value; // Update existing value
                return;
            }
            current = current.next;
        }

        // Key not found, insert new node at head of chain (Simpler than tail)
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index]; // Current head becomes next
        buckets[index] = newNode;      // New node becomes head
        size++;
    }

    // GET Method
    public V get(K key) {
        int index = getBucketIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Key not found
    }

    // Hash Logic
    private int getBucketIndex(K key) {
        int hashCode = (key == null) ? 0 : key.hashCode();
        // Use Math.abs to ensure positive index
        return Math.abs(hashCode) % buckets.length; 
    }
    
    public int size() {
        return size;
    }
}
```

## 4. USAGE

```java
public class TestMap {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("A", 100); // Update
        
        System.out.println(map.get("A")); // Output: 100
        System.out.println(map.get("B")); // Output: 2
    }
}
```