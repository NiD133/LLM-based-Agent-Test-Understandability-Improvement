package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringNull {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("apply() throws IllegalArgumentException when left input is null")
    void testGetLevenshteinDistance_StringNull(final Class<?> cls) {
        @SuppressWarnings("rawtypes")
        SimilarityInput nullLeft = SimilarityInputTest.build(cls, null);
        @SuppressWarnings("rawtypes")
        SimilarityInput validRight = SimilarityInputTest.build(cls, "a");

        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(nullLeft, validRight));
    }
}
