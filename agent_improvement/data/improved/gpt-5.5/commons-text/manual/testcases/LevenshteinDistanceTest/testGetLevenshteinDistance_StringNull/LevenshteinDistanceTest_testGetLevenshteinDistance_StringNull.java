package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringNull {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringNull(final Class<?> cls) {
        assertNullLeftInputIsRejected(cls);
    }

    private static void assertNullLeftInputIsRejected(final Class<?> cls) {
        final SimilarityInput<Object> nullLeftInput = SimilarityInputTest.build(cls, null);
        final SimilarityInput<Object> nonNullRightInput = SimilarityInputTest.build(cls, "a");

        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(nullLeftInput, nonNullRightInput));
    }
}
