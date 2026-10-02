package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {
        assertDetailedDistance(cls, "", "", 0, 0, 0, 0);
        assertDetailedDistance(cls, "", "a", 1, 1, 0, 0);
        assertDetailedDistance(cls, "aaapppp", "", 7, 0, 7, 0);
        assertDetailedDistance(cls, "frog", "fog", 1, 0, 1, 0);
        assertDetailedDistance(cls, "fly", "ant", 3, 0, 0, 3);
        assertDetailedDistance(cls, "elephant", "hippo", 7, 0, 3, 4);
        assertDetailedDistance(cls, "hippo", "elephant", 7, 3, 0, 4);
        assertDetailedDistance(cls, "hippo", "zzzzzzzz", 8, 3, 0, 5);
        assertDetailedDistance(cls, "zzzzzzzz", "hippo", 8, 0, 3, 5);
        assertDetailedDistance(cls, "hello", "hallo", 1, 0, 0, 1);
    }

    private static void assertDetailedDistance(final Class<?> inputType, final String left, final String right, final int distance,
            final int insertions, final int deletions, final int substitutions) {
        final LevenshteinResults result = UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(inputType, left),
                SimilarityInputTest.build(inputType, right));

        assertEquals(distance, result.getDistance());
        assertEquals(insertions, result.getInsertCount());
        assertEquals(deletions, result.getDeleteCount());
        assertEquals(substitutions, result.getSubstituteCount());
    }
}
