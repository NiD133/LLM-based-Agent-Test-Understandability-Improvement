package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance(final Class<?> cls) {
        assertDistance(cls, 0, "", "");
        assertDistance(cls, 1, "", "a");
        assertDistance(cls, 7, "aaapppp", "");
        assertDistance(cls, 1, "frog", "fog");
        assertDistance(cls, 3, "fly", "ant");
        assertDistance(cls, 7, "elephant", "hippo");
        assertDistance(cls, 7, "hippo", "elephant");
        assertDistance(cls, 8, "hippo", "zzzzzzzz");
        assertDistance(cls, 8, "zzzzzzzz", "hippo");
        assertDistance(cls, 1, "hello", "hallo");
    }

    private static void assertDistance(final Class<?> cls, final int expectedDistance, final String left, final String right) {
        assertEquals(expectedDistance,
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right)));
    }
}
