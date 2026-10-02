package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testToString {

    private static final LevenshteinDetailedDistance DEFAULT_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void testToString() {
        assertResultToString("fly", "ant", new LevenshteinResults(3, 0, 0, 3));
        assertResultToString("hippo", "elephant", new LevenshteinResults(7, 3, 0, 4));
        assertResultToString("", "a", new LevenshteinResults(1, 1, 0, 0));
    }

    private void assertResultToString(final String left, final String right, final LevenshteinResults expectedResult) {
        final LevenshteinResults actualResult = DEFAULT_DISTANCE.apply(left, right);

        assertEquals(expectedResult.toString(), actualResult.toString());
    }
}
