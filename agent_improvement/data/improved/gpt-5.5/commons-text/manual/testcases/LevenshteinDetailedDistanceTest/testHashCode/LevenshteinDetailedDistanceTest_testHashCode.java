package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testHashCode {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void testHashCode() {
        assertHashCodeMatchesExpectedResult("aaapppp", "", new LevenshteinResults(7, 0, 7, 0));
        assertHashCodeMatchesExpectedResult("frog", "fog", new LevenshteinResults(1, 0, 1, 0));
        assertHashCodeMatchesExpectedResult("elephant", "hippo", new LevenshteinResults(7, 0, 3, 4));
    }

    private void assertHashCodeMatchesExpectedResult(final String left, final String right, final LevenshteinResults expectedResult) {
        final LevenshteinResults actualResult = UNLIMITED_DISTANCE.apply(left, right);

        assertEquals(expectedResult.hashCode(), actualResult.hashCode());
    }
}
