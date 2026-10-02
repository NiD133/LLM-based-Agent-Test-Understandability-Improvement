package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_NullString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    @DisplayName("apply() throws IllegalArgumentException when the right argument is null")
    void testGetLevenshteinDetailedDistance_NullString() {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply("a", null));
    }
}
