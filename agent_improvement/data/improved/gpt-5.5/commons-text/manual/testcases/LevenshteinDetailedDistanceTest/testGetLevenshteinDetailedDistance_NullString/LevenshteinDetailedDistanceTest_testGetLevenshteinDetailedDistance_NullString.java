package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_NullString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();
    private static final String NON_NULL_LEFT_INPUT = "a";

    @Test
    void testGetLevenshteinDetailedDistance_NullString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(NON_NULL_LEFT_INPUT, null));
    }
}
