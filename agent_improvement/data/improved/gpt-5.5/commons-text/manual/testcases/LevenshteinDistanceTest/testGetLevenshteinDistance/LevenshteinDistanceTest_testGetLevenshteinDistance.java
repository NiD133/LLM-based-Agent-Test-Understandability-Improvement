package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance(final Class<?> cls) {
        assertDistance(cls, "", "", 0);
        assertDistance(cls, "", "a", 1);
        assertDistance(cls, "aaapppp", "", 7);
        assertDistance(cls, "frog", "fog", 1);
        assertDistance(cls, "fly", "ant", 3);
        assertDistance(cls, "elephant", "hippo", 7);
        assertDistance(cls, "hippo", "elephant", 7);
        assertDistance(cls, "hippo", "zzzzzzzz", 8);
        assertDistance(cls, "zzzzzzzz", "hippo", 8);
        assertDistance(cls, "hello", "hallo", 1);
    }

    private static void assertDistance(final Class<?> cls, final String left, final String right, final int expectedDistance) {
        assertEquals(
                expectedDistance,
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right)));
    }
}
