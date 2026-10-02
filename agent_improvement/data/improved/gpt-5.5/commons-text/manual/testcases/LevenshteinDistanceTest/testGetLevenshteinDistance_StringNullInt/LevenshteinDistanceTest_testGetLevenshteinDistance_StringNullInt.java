package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringNullInt {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();
    private static final String NON_NULL_LEFT_INPUT = "a";

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringNullInt(final Class<?> inputType) {
        assertThrows(IllegalArgumentException.class, () -> applyDistanceWithNullRightInput(inputType));
    }

    private static Integer applyDistanceWithNullRightInput(final Class<?> inputType) {
        return UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(inputType, NON_NULL_LEFT_INPUT),
                SimilarityInputTest.build(inputType, null));
    }
}
