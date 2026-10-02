package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testHashCode {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void testHashCode() {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();
        LevenshteinResults actualResult = classBeingTested.apply("aaapppp", "");
        LevenshteinResults expectedResult = new LevenshteinResults(7, 0, 7, 0);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());
        actualResult = classBeingTested.apply("frog", "fog");
        expectedResult = new LevenshteinResults(1, 0, 1, 0);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());
        actualResult = classBeingTested.apply("elephant", "hippo");
        expectedResult = new LevenshteinResults(7, 0, 3, 4);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());
    }
}
