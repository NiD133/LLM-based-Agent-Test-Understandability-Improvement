package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringNull {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringNull(final Class<?> cls) {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, null), SimilarityInputTest.build(cls, "a")));
    }
}
