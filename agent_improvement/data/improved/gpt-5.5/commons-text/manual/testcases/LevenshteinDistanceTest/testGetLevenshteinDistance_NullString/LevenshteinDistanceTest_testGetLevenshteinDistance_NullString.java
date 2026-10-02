package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_NullString {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();
    private static final String LEFT_INPUT = "a";
    private static final String NULL_RIGHT_INPUT = null;

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_NullString(final Class<?> cls) {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, LEFT_INPUT),
                SimilarityInputTest.build(cls, NULL_RIGHT_INPUT)));
    }
}
