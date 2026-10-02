package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testToString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void testToString() {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();
        LevenshteinResults actualResult = classBeingTested.apply("fly", "ant");
        LevenshteinResults expectedResult = new LevenshteinResults(3, 0, 0, 3);
        assertEquals(expectedResult.toString(), actualResult.toString());
        actualResult = classBeingTested.apply("hippo", "elephant");
        expectedResult = new LevenshteinResults(7, 3, 0, 4);
        assertEquals(expectedResult.toString(), actualResult.toString());
        actualResult = classBeingTested.apply("", "a");
        expectedResult = new LevenshteinResults(1, 1, 0, 0);
        assertEquals(expectedResult.toString(), actualResult.toString());
    }
}
