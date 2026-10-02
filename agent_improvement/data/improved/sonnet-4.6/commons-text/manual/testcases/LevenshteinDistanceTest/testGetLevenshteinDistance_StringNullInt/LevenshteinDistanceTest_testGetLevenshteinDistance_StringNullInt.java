package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringNullInt {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    /**
     * Verifies that passing null as the right-hand input to apply() throws
     * IllegalArgumentException, regardless of the SimilarityInput implementation type.
     */
    @ParameterizedTest(name = "apply(\"a\", null) throws IllegalArgumentException for input type {0}")
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("apply() throws IllegalArgumentException when the right input is null")
    void testGetLevenshteinDistance_StringNullInt(final Class<?> inputType) {
        SimilarityInput<Object> leftInput  = SimilarityInputTest.build(inputType, "a");
        SimilarityInput<Object> rightInput = SimilarityInputTest.build(inputType, null); // null triggers the exception

        assertThrows(
            IllegalArgumentException.class,
            () -> UNLIMITED_DISTANCE.apply(leftInput, rightInput),
            "apply() must reject a null right-hand SimilarityInput"
        );
    }
}
