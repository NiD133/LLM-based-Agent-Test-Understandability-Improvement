package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringString {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringString(final Class<?> cls) {
        assertEquals(0, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "")));
        assertEquals(1, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "a")));
        assertEquals(7, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")));
        assertEquals(1, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")));
        assertEquals(3, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant")));
        assertEquals(7, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(7, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "elephant")));
        assertEquals(8, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "zzzzzzzz")));
        assertEquals(8, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(1, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo")));
    }
}
