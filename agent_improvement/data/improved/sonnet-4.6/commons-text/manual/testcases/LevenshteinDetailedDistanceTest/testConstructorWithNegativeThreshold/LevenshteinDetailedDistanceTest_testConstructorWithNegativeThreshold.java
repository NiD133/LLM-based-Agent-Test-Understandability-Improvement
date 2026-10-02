package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testConstructorWithNegativeThreshold {

    @Test
    @DisplayName("Constructor rejects a negative threshold with IllegalArgumentException")
    void testConstructorWithNegativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(-1));
    }
}
