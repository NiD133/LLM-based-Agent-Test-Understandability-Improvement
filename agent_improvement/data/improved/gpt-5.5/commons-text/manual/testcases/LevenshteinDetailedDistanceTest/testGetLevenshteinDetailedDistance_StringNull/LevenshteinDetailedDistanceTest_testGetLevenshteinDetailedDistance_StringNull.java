package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringNull {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void throwsIllegalArgumentExceptionWhenLeftInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(null, "a"));
    }
}
