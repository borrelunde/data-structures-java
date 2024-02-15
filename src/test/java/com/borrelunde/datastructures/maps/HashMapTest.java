package com.borrelunde.datastructures.maps;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

/**
 * HashMapTest is a test class for the HashMap class.
 *
 * @author Børre A. Opedal Lunde
 * @since 2024.02.10
 */
@DisplayName("Hash Map")
class HashMapTest implements MapTest<Integer, String> {

	@Override
	public Map<Integer, String> createMap() {
		return new HashMap<>();
	}

	@Override
	public Integer sampleKey() {
		return 0;
	}

	@Override
	public String sampleValue() {
		return "Romulus";
	}

	@Override
	public List<Integer> sampleKeys(final int count) {
		return List.of(
				1,
				2,
				3,
				4,
				5,
				6,
				7,
				8,
				9,
				10,
				11,
				12,
				13,
				14,
				15,
				16,
				17,
				18,
				19,
				20
		);
	}

	@Override
	public List<String> sampleValues(final int count) {
		return List.of(
				"Augustus",
				"Tiberius",
				"Caligula",
				"Claudius",
				"Nero",
				"Galba",
				"Otho",
				"Vitellius",
				"Vespasian",
				"Titus",
				"Domitian",
				"Nerva",
				"Trajan",
				"Hadrian",
				"Antoninus Pius",
				"Marcus Aurelius",
				"Lucius Verus",
				"Commodus",
				"Pertinax",
				"Didius Julianus"
		);
	}
}