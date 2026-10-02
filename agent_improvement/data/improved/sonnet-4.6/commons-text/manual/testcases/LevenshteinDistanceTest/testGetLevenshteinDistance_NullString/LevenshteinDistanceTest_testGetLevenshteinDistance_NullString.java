package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_NullString {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("apply() throws IllegalArgumentException when the second input is null")
    void testGetLevenshteinDistance_NullString(final Class<?> cls) {
        SimilarityInput<Object> left  = SimilarityInputTest.build(cls, "a");
        SimilarityInput<Object> right = SimilarityInputTest.build(cls, null); // null triggers the guard

        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(left, right));
    }
}
