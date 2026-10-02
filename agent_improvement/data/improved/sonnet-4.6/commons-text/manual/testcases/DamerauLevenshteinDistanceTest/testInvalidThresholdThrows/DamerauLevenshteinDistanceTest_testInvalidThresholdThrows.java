package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testInvalidThresholdThrows {

    @Test
    void testInvalidThresholdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DamerauLevenshteinDistance(-1));
    }
}
