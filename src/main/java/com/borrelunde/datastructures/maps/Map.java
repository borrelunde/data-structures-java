package com.borrelunde.datastructures.maps;

/**
 * Map is an interface that stores key-value pairs.
 *
 * @author Børre A. Opedal Lunde
 * @since 2024.02.10
 */
public interface Map<Key, Value> {

	int size();

	boolean isEmpty();

	boolean containsKey(Key key);

	boolean containsValue(Value value);

	Value get(Key key);

	void put(Key key, Value value);

	void remove(Key key);

	void clear();

	interface Entry<Key, Value> {
		Key getKey();

		Value getValue();

		boolean equals(Object other);

		int hashCode();
	}
}
