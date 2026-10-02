package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_NullStringInt {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_NullStringInt(final Class<?> similarityInputType) {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(similarityInputType, null),
                SimilarityInputTest.build(similarityInputType, "a")));
    }
}
