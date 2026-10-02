package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringNullInt {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    @DisplayName("apply(String, null) throws IllegalArgumentException when right input is null")
    void testGetLevenshteinDetailedDistance_StringNullInt() {
        // The contract requires both inputs to be non-null; passing null as the right operand must throw.
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply("a", null));
    }
}
