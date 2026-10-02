package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_NullStringInt {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    @DisplayName("apply(null, right) throws IllegalArgumentException when left input is null")
    void testGetLevenshteinDetailedDistance_NullStringInt() {
        // The API contract requires both inputs to be non-null; a null left argument must throw.
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(null, "a"));
    }
}
