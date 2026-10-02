package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringStringInt {

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringStringInt(final Class<?> cls) {
        assertEmptyStringDistances(cls);
        assertZeroThresholdDistances(cls);
        assertEqualStringDistances(cls);
        assertSameLengthDistances(cls);
        assertDistancesWithinThreshold(cls);
        assertDistancesAtThreshold(cls);
        assertDistancesBeyondThreshold(cls);
        assertStripeRunOffDistances(cls);
        assertLegacyBoundedDistances(cls);
        assertLegacyEffectivelyUnboundedDistances(cls);
    }

    private static void assertEmptyStringDistances(final Class<?> cls) {
        assertDistance(cls, 0, 0, "", "");
        assertDistance(cls, 7, 8, "aaapppp", "");
        assertDistance(cls, 7, 7, "aaapppp", "");
        assertDistance(cls, -1, 6, "aaapppp", "");
    }

    private static void assertZeroThresholdDistances(final Class<?> cls) {
        assertDistance(cls, -1, 0, "b", "a");
        assertDistance(cls, -1, 0, "a", "b");
    }

    private static void assertEqualStringDistances(final Class<?> cls) {
        assertDistance(cls, 0, 0, "aa", "aa");
        assertDistance(cls, 0, 2, "aa", "aa");
    }

    private static void assertSameLengthDistances(final Class<?> cls) {
        assertDistance(cls, -1, 2, "aaa", "bbb");
        assertDistance(cls, 3, 3, "aaa", "bbb");
    }

    private static void assertDistancesWithinThreshold(final Class<?> cls) {
        assertDistance(cls, 6, 10, "aaaaaa", "b");
        assertDistance(cls, 7, 8, "aaapppp", "b");
        assertDistance(cls, 3, 4, "a", "bbb");
    }

    private static void assertDistancesAtThreshold(final Class<?> cls) {
        assertDistance(cls, 7, 7, "aaapppp", "b");
        assertDistance(cls, 3, 3, "a", "bbb");
    }

    private static void assertDistancesBeyondThreshold(final Class<?> cls) {
        assertDistance(cls, -1, 2, "a", "bbb");
        assertDistance(cls, -1, 2, "bbb", "a");
        assertDistance(cls, -1, 6, "aaapppp", "b");
    }

    private static void assertStripeRunOffDistances(final Class<?> cls) {
        assertDistance(cls, -1, 1, "a", "bbb");
        assertDistance(cls, -1, 1, "bbb", "a");
        assertDistance(cls, -1, 1, "12345", "1234567");
        assertDistance(cls, -1, 1, "1234567", "12345");
    }

    private static void assertLegacyBoundedDistances(final Class<?> cls) {
        assertDistance(cls, 1, 1, "frog", "fog");
        assertDistance(cls, 3, 3, "fly", "ant");
        assertDistance(cls, 7, 7, "elephant", "hippo");
        assertDistance(cls, -1, 6, "elephant", "hippo");
        assertDistance(cls, 7, 7, "hippo", "elephant");
        assertDistance(cls, -1, 6, "hippo", "elephant");
        assertDistance(cls, 8, 8, "hippo", "zzzzzzzz");
        assertDistance(cls, 8, 8, "zzzzzzzz", "hippo");
        assertDistance(cls, 1, 1, "hello", "hallo");
        assertDistance(cls, -1, 1, "abc", "acb");
    }

    private static void assertLegacyEffectivelyUnboundedDistances(final Class<?> cls) {
        assertDistance(cls, 1, Integer.MAX_VALUE, "frog", "fog");
        assertDistance(cls, 3, Integer.MAX_VALUE, "fly", "ant");
        assertDistance(cls, 7, Integer.MAX_VALUE, "elephant", "hippo");
        assertDistance(cls, 7, Integer.MAX_VALUE, "hippo", "elephant");
        assertDistance(cls, 8, Integer.MAX_VALUE, "hippo", "zzzzzzzz");
        assertDistance(cls, 8, Integer.MAX_VALUE, "zzzzzzzz", "hippo");
        assertDistance(cls, 1, Integer.MAX_VALUE, "hello", "hallo");
    }

    private static void assertDistance(
        final Class<?> cls,
        final int expectedDistance,
        final int threshold,
        final String left,
        final String right) {

        assertEquals(
            expectedDistance,
            new LevenshteinDistance(threshold).apply(
                SimilarityInputTest.build(cls, left),
                SimilarityInputTest.build(cls, right)));
    }
}
