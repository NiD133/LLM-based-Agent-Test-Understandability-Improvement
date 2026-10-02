package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetDefaultInstanceOne {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceOne(final Class<?> cls) {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply(SimilarityInputTest.build(cls, "Distance: -2147483643, Insert: 0, Delete: 0, Substitute: 0"), SimilarityInputTest.build(cls, "Distance: 0, Insert: 2147483536, Delete: 0, Substitute: 0"));
        assertEquals(21, levenshteinResults.getDistance());
    }
}
