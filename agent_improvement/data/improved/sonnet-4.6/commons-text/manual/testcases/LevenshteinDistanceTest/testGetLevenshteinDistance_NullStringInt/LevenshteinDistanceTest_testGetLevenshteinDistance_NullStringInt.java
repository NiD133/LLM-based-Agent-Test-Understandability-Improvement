package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_NullStringInt {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    /**
     * Verifies that apply() throws IllegalArgumentException when the left (first) input is null,
     * regardless of the SimilarityInput implementation type.
     */
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("apply() throws IllegalArgumentException when left input is null")
    void testGetLevenshteinDistance_NullStringInt(final Class<?> cls) {
        SimilarityInput<Object> nullLeft = SimilarityInputTest.build(cls, null);
        SimilarityInput<Object> validRight = SimilarityInputTest.build(cls, "a");

        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(nullLeft, validRight));
    }
}
