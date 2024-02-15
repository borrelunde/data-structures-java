package com.borrelunde.datastructures.maps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MapTest is an interface all maps should implement. It contains standard tests
 * that apply to all maps, so that they don't have to be implemented again and
 * again for every map.
 *
 * @author Børre A. Opedal Lunde
 * @since 2024.02.10
 */
interface MapTest<Key, Value> {

	Map<Key, Value> createMap();

	Key sampleKey();

	Value sampleValue();

	List<Key> sampleKeys(int count);

	List<Value> sampleValues(int count);

	@Test
	@DisplayName("A new map should not be null")
	default void aNewMapShouldNotBeNull() {
		Map<Key, Value> map = createMap();

		assertNotNull(map, "Map should not be null.");
	}

	@Test
	@DisplayName("A new map should be empty")
	default void aNewMapShouldBeEmpty() {
		Map<Key, Value> map = createMap();

		assertTrue(map.isEmpty(), "Map should be empty.");
	}

	@Test
	@DisplayName("A new map's size should be zero")
	default void aNewMapSSizeShouldBeZero() {
		Map<Key, Value> map = createMap();

		assertEquals(0, map.size(), "Map's size should be zero.");
	}

	@Test
	@DisplayName("Inserting a single entry should increase size to 1")
	default void insertingASingleEntryShouldIncreaseSizeToOne() {
		Map<Key, Value> map = createMap();

		map.put(sampleKey(), sampleValue());

		assertEquals(1, map.size(), "Map's size should be 1 after inserting a single entry.");
	}

	@Test
	@DisplayName("Inserting a single entry should contain that key")
	default void insertingASingleEntryShouldContainThatKey() {
		Map<Key, Value> map = createMap();
		Key key = sampleKey();

		map.put(key, sampleValue());

		assertTrue(map.containsKey(key), "Map should contain the key after inserting a single entry.");
	}

	@Test
	@DisplayName("Inserting a single entry should contain that value")
	default void insertingASingleEntryShouldContainThatValue() {
		Map<Key, Value> map = createMap();
		Value value = sampleValue();

		map.put(sampleKey(), value);

		assertTrue(map.containsValue(value), "Map should contain the value after inserting a single entry.");
	}

	@Test
	@DisplayName("Inserting a single entry should make the map not empty")
	default void insertingASingleEntryShouldMakeTheMapNotEmpty() {
		Map<Key, Value> map = createMap();

		map.put(sampleKey(), sampleValue());

		assertFalse(map.isEmpty(), "Map should not be empty after inserting a single entry.");
	}

	@Test
	@DisplayName("Inserting multiple unique entries should increase size by the number of entries")
	default void insertingMultipleUniqueEntriesShouldIncreaseSizeByTheNumberOfEntries() {
		Map<Key, Value> map = createMap();
		List<Key> keys = sampleKeys(3);
		List<Value> values = sampleValues(3);

		for (int i = 0; i < 3; i++) {
			map.put(keys.get(i), values.get(i));
		}

		assertEquals(3, map.size(), "Map's size should be 3 after inserting three unique entries.");
	}

	@Test
	@DisplayName("Inserting multiple unique entries should contain all keys")
	default void insertingMultipleUniqueEntriesShouldContainAllKeys() {
		Map<Key, Value> map = createMap();
		List<Key> keys = sampleKeys(3);
		List<Value> values = sampleValues(3);

		for (int i = 0; i < 3; i++) {
			map.put(keys.get(i), values.get(i));
		}

		for (int i = 0; i < 3; i++) {
			assertTrue(map.containsKey(keys.get(i)), "Map should contain key " + keys.get(i) + " after inserting three unique entries.");
		}
	}

	@Test
	@DisplayName("Inserting multiple unique entries should contain all values")
	default void insertingMultipleUniqueEntriesShouldContainAllValues() {
		Map<Key, Value> map = createMap();
		List<Key> keys = sampleKeys(3);
		List<Value> values = sampleValues(3);

		for (int i = 0; i < 3; i++) {
			map.put(keys.get(i), values.get(i));
		}

		for (int i = 0; i < 3; i++) {
			assertTrue(map.containsValue(values.get(i)), "Map should contain value " + values.get(i) + " after inserting three unique entries.");
		}
	}

	@Test
	@DisplayName("Inserting two entries with the same key should replace the first with the second")
	default void insertingTwoEntriesWithTheSameKeyShouldReplaceTheFirstWithTheSecond() {
		Map<Key, Value> map = createMap();
		Key key = sampleKey();
		Value value1 = sampleValue();
		Value value2 = sampleValue();

		map.put(key, value1);
		map.put(key, value2);

		assertEquals(value2, map.get(key), "Map should contain the second value after inserting two entries with the same key.");
	}

	@Test
	@DisplayName("Inserting two entries with the same key should not increase size")
	default void insertingTwoEntriesWithTheSameKeyShouldNotIncreaseSize() {
		Map<Key, Value> map = createMap();
		Key key = sampleKey();
		Value value1 = sampleValue();
		Value value2 = sampleValue();

		map.put(key, value1);
		map.put(key, value2);

		assertEquals(1, map.size(), "Map's size should be 1 after inserting two entries with the same key.");
	}

	@Test
	@DisplayName("Inserting two entries with the same key should contain the key")
	default void insertingTwoEntriesWithTheSameKeyShouldContainTheKey() {
		Map<Key, Value> map = createMap();
		Key key = sampleKey();
		Value value1 = sampleValue();
		Value value2 = sampleValue();

		map.put(key, value1);
		map.put(key, value2);

		assertTrue(map.containsKey(key), "Map should contain the key after inserting two entries with the same key.");
	}

	@Test
	@DisplayName("Removing a key in the map should remove that pair")
	default void removingAKeyInTheMapShouldRemoveThatPair() {
		Map<Key, Value> map = createMap();

		Key key = sampleKey();
		Value value = sampleValue();

		map.put(key, value);
		map.remove(key);

		assertFalse(map.containsKey(key), "Map should not contain key after removal.");
	}

	@Test
	@DisplayName("Removing a key not in the map should remove nothing")
	default void removingAKeyNotInTheMapShouldRemoveNothing() {
		Map<Key, Value> map = createMap();

		Key key = sampleKey();
		Value value = sampleValue();

		map.put(key, value);
		map.remove(sampleKeys(2).get(1));

		assertTrue(map.containsKey(key), "Map should contain key after removing a non-existent key.");
	}

	@Test
	@DisplayName("Getting an inserted value by key should return the correct value")
	default void gettingAnInsertedValueByKeyShouldReturnTheCorrectValue() {
		Map<Key, Value> map = createMap();
		Key key = sampleKey();
		Value value = sampleValue();

		map.put(key, value);

		assertEquals(value, map.get(key), "Retrieving the value of an inserted key should return the correct value.");
	}

	@Test
	@DisplayName("Getting a non-existent key should return null")
	default void gettingANonExistentKeyShouldReturnNull() {
		Map<Key, Value> map = createMap();

		assertNull(map.get(sampleKey()), "Retrieving the value of a non-existent key should return null.");
	}

	@Test
	@DisplayName("Map should contain inserted key")
	default void mapShouldContainInsertedKey() {
		Map<Key, Value> map = createMap();
		Key key = sampleKey();

		map.put(key, sampleValue());

		assertTrue(map.containsKey(key), "Map should contain the inserted key.");
	}

	@Test
	@DisplayName("Map should contain inserted value")
	default void mapShouldContainInsertedValue() {
		Map<Key, Value> map = createMap();
		Value value = sampleValue();

		map.put(sampleKey(), value);

		assertTrue(map.containsValue(value), "Map should contain the inserted value.");
	}

	@Test
	@DisplayName("Map should not contain non-existent key")
	default void mapShouldNotContainNonExistentKey() {
		Map<Key, Value> map = createMap();

		assertFalse(map.containsKey(sampleKey()), "Map should not contain a non-existent key.");
	}

	@Test
	@DisplayName("Map should not contain non-existent value")
	default void mapShouldNotContainNonExistentValue() {
		Map<Key, Value> map = createMap();

		assertFalse(map.containsValue(sampleValue()), "Map should not contain a non-existent value.");
	}

	@Test
	@DisplayName("Clearing a map should result in an empty map")
	default void clearingAMapShouldResultInAnEmptyMap() {
		Map<Key, Value> map = createMap();
		map.put(sampleKey(), sampleValue());

		map.clear();

		assertTrue(map.isEmpty(), "Map should be empty after clearing.");
	}

	@Test
	@DisplayName("Clearing a map should reset size to zero")
	default void clearingAMapShouldResetSizeToZero() {
		Map<Key, Value> map = createMap();
		map.put(sampleKey(), sampleValue());

		map.clear();

		assertEquals(0, map.size(), "Map's size should be zero after clearing.");
	}

	@Test
	@DisplayName("Clearing an empty map should work")
	default void clearingAnEmptyMapShouldWork() {
		Map<Key, Value> map = createMap();

		map.clear();

		assertTrue(map.isEmpty(), "Clearing an empty map should work.");
	}

	@Test
	@DisplayName("Clearing a map should remove all pairs")
	default void clearingAMapShouldRemoveAllPairs() {
		Map<Key, Value> map = createMap();
		map.put(sampleKey(), sampleValue());

		map.clear();

		assertFalse(map.containsKey(sampleKey()), "Map should not contain any pairs after clearing.");
		assertFalse(map.containsValue(sampleValue()), "Map should not contain any pairs after clearing.");
		assertEquals(0, map.size(), "Map's size should be zero after clearing.");
	}

	@Test
	@DisplayName("Map should support null values")
	default void mapShouldSupportsNullValues() {
		Map<Key, Value> map = createMap();
		Key key = sampleKey();

		map.put(key, null);
		assertTrue(map.containsKey(key), "Map should contain the key associated with a null value.");
		assertNull(map.get(key), "Retrieving the value of a key associated with a null value should return null.");
	}
}
