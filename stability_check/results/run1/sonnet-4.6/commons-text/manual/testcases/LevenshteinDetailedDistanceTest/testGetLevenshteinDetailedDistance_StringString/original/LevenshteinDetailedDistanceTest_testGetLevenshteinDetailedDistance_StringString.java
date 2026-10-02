package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {
        LevenshteinResults result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, ""));
        assertEquals(0, result.getDistance());
        assertEquals(0, result.getInsertCount());
        assertEquals(0, result.getDeleteCount());
        assertEquals(0, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "a"));
        assertEquals(1, result.getDistance());
        assertEquals(1, result.getInsertCount());
        assertEquals(0, result.getDeleteCount());
        assertEquals(0, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, ""));
        assertEquals(7, result.getDistance());
        assertEquals(0, result.getInsertCount());
        assertEquals(7, result.getDeleteCount());
        assertEquals(0, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog"));
        assertEquals(1, result.getDistance());
        assertEquals(0, result.getInsertCount());
        assertEquals(1, result.getDeleteCount());
        assertEquals(0, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant"));
        assertEquals(3, result.getDistance());
        assertEquals(0, result.getInsertCount());
        assertEquals(0, result.getDeleteCount());
        assertEquals(3, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo"));
        assertEquals(7, result.getDistance());
        assertEquals(0, result.getInsertCount());
        assertEquals(3, result.getDeleteCount());
        assertEquals(4, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "elephant"));
        assertEquals(7, result.getDistance());
        assertEquals(3, result.getInsertCount());
        assertEquals(0, result.getDeleteCount());
        assertEquals(4, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "zzzzzzzz"));
        assertEquals(8, result.getDistance());
        assertEquals(3, result.getInsertCount());
        assertEquals(0, result.getDeleteCount());
        assertEquals(5, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo"));
        assertEquals(8, result.getDistance());
        assertEquals(0, result.getInsertCount());
        assertEquals(3, result.getDeleteCount());
        assertEquals(5, result.getSubstituteCount());
        result = UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo"));
        assertEquals(1, result.getDistance());
        assertEquals(0, result.getInsertCount());
        assertEquals(0, result.getDeleteCount());
        assertEquals(1, result.getSubstituteCount());
    }
}
