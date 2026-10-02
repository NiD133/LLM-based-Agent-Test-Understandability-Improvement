package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringNull {

    // Unlimited-distance instance (no threshold); applies the full Levenshtein algorithm.
    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    /**
     * Passing null as the left input must throw IllegalArgumentException because the
     * algorithm requires both character sequences to be non-null.
     */
    @Test
    void testGetLevenshteinDetailedDistance_StringNull() {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(null, "a"));
    }
}
