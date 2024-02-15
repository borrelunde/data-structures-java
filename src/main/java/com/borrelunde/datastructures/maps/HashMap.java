package com.borrelunde.datastructures.maps;

/**
 * HashMap is a data structure that stores key-value pairs. It is a hash table
 * based implementation of the Map interface.
 *
 * @author Børre A. Opedal Lunde
 * @since 2024.02.10
 */
public class HashMap<Key, Value> implements Map<Key, Value> {

	// The default initial capacity of the map.
	private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;  // 16

	// The minimum capacity of the map.
	private static final int MINIMUM_CAPACITY = 1 << 2; // 4

	// The maximum capacity of the map.
	private static final int MAXIMUM_CAPACITY = 1 << 30; // 1,073,741,824 (roughly a billion)

	// The default load factor of the map. The load factor is a measure of how
	// full the map is allowed to get before its capacity is increased.
	// 0.75 represents 75% of the capacity.
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	private static class Node<Key, Value> implements Map.Entry<Key, Value> {
		private final Key key;
		private Value value;
		private Node<Key, Value> next = null;  // Closed addressing, chaining.

		public Node(Key key, Value value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public Key getKey() {
			return key;
		}

		@Override
		public Value getValue() {
			return value;
		}

		public boolean hasNext() {
			return next != null;
		}
	}

	private int hash(Object key) {
		// If the key is null, return 0.
		if (key == null) {
			return 0;
		}

		// Calculate the initial hash code of the key.
		int hash = key.hashCode();

		// Ensure the hash is positive.
		hash = hash < 0 ? - hash : hash;

		// The hash value is not guaranteed to be within the bounds of the
		// array. Therefore, we use the modulo operator to ensure it is. For
		// example, if the capacity is 16, this will ensure the hash is between
		// 0 and 15.
		return hash % capacity;
	}

	private static int calculateThreshold(int capacity, float loadFactor) {
		return (int) (capacity * loadFactor);
	}

	private Node<Key, Value>[] buckets;
	private int capacity;
	private final float loadFactor;
	private int threshold;
	private int size;

	@SuppressWarnings("unchecked")
	public HashMap(int capacity, float loadFactor) {

		// Ensure the capacity is within the bounds of the minimum and maximum.
		if (capacity < MINIMUM_CAPACITY) {
			capacity = MINIMUM_CAPACITY;
		} else if (capacity > MAXIMUM_CAPACITY) {
			capacity = MAXIMUM_CAPACITY;
		}
		this.capacity = capacity;

		// Create an array of buckets with the given capacity.
		this.buckets = (Node<Key, Value>[]) new Node[capacity];

		// Ensure the load factor is within the bounds of 0 and 1.
		if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
			// If not, use the default load factor.
			loadFactor = DEFAULT_LOAD_FACTOR;
		}
		this.loadFactor = loadFactor;

		// Calculate the threshold based on the capacity and the load factor.
		this.threshold = calculateThreshold(capacity, loadFactor);

		// Initialize the size to 0.
		this.size = 0;
	}

	public HashMap(int capacity) {
		this(capacity, DEFAULT_LOAD_FACTOR);
	}

	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Key key) {
		return getNode(key) != null;
	}

	@Override
	public boolean containsValue(Value value) {
		// Iterate through the buckets and check if any of them contains the
		// value.
		for (Node<Key, Value> bucket : buckets) {
			while (bucket != null) {
				if (bucket.value.equals(value)) {
					return true;
				}
				bucket = bucket.next;
			}
		}
		return false;
	}

	@Override
	public Value get(Key key) {
		final Node<Key, Value> node = getNode(key);
		return node == null ? null : node.value;
	}

	private Node<Key, Value> getNode(Key key) {
		// Calculate the index in the bucket array for this key.
		final int index = hash(key);
		Node<Key, Value> node = buckets[index];

		// Iterate through the nodes in the bucket and look for
		// the node whose key equals the target key.
		while (node != null) {
			if (node.key.equals(key)) {
				return node;
			}
			node = node.next;
		}

		// If not found, return null.
		return null;
	}

	@Override
	public void put(Key key, Value value) {
		// Calculate the index in the bucket array for this key.
		final int index = hash(key);
		Node<Key, Value> node = buckets[index];

		// If the bucket is empty, create a new node and place it in the bucket.
		if (node == null) {
			buckets[index] = new Node<>(key, value);
		} else {
			// Otherwise, iterate through the nodes in the bucket.
			while (true) {
				// If a node with the same key is found, overwrite its value.
				if (node.key.equals(key)) {
					node.value = value;
					return;
				}
				// If the end of the chain is reached, add a new node at the end.
				if (node.next == null) {
					node.next = new Node<>(key, value);
					break;
				}
				// Move to the next node in the chain.
				node = node.next;
			}
		}

		// If the size of the map exceeds the threshold, resize the map.
		if (++ size > threshold) {
			resize();
		}
	}

	@Override
	public void remove(Key key) {
		// Calculate the index in the bucket array for this key.
		final int index = hash(key);
		Node<Key, Value> node = buckets[index];

		// If the bucket is empty, the key is not in the map.
		if (node == null) {
			return;
		}

		// If the first node in the bucket has the key, remove it.
		if (node.key.equals(key)) {
			buckets[index] = node.next;
			size--;
			return;
		}

		// Otherwise, iterate through the bucket, looking for the node
		// that has the 'next' node with the key.
		while (node.hasNext()) {
			if (node.next.key.equals(key)) {
				// If that node is found, remove the 'next' node.
				node.next = node.next.next;
				size--;
				return;
			}
			node = node.next;
		}
	}

	@SuppressWarnings("ExplicitArrayFilling")
	@Override
	public void clear() {
		// Iterate over the buckets and set each bucket to null.
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = null;
		}
		// Reset the size to 0.
		size = 0;
	}

	private void resize() {

		// Double the current capacity, up to the maximum capacity.
		int newCapacity = capacity * 2;
		if (newCapacity > MAXIMUM_CAPACITY) {
			newCapacity = MAXIMUM_CAPACITY;
		}

		// Create a new array of buckets with the new capacity.
		@SuppressWarnings("unchecked")
		Node<Key, Value>[] newBuckets = (Node<Key, Value>[]) new Node[newCapacity];

		// Rehash the entries from the old buckets array into the new one.
		for (Node<Key, Value> oldBucket : buckets) {
			while (oldBucket != null) {
				// The next node will be overwritten later, so we save it first.
				// That way we can continue iterating through the old bucket.
				Node<Key, Value> next = oldBucket.next;

				// Calculate the new index for the node.
				int newIndex = hash(oldBucket.key) % newCapacity;

				// Insert the node into the new buckets array at the new index.
				// This overwrites the next node, so it is important that we
				// saved it before.
				oldBucket.next = newBuckets[newIndex];
				newBuckets[newIndex] = oldBucket;

				// Move to the next node in the old bucket.
				oldBucket = next;
			}
		}

		// Replace the old buckets with the new buckets.
		buckets = newBuckets;
		capacity = newCapacity;

		// Update the threshold based on the new capacity and load factor.
		threshold = calculateThreshold(newCapacity, loadFactor);
	}
}
