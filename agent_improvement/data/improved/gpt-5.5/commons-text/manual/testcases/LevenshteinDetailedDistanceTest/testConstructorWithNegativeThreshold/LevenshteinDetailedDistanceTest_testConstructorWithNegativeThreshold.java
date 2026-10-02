package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testConstructorWithNegativeThreshold {

    private static final int NEGATIVE_THRESHOLD = -1;

    @Test
    void testConstructorWithNegativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(NEGATIVE_THRESHOLD));
    }
}
