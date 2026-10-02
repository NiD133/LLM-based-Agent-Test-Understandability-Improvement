package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringStringInt {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringStringInt(final Class<?> cls) {
        // empty strings
        assertEquals(0, new LevenshteinDistance(0).apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "")));
        assertEquals(7, new LevenshteinDistance(8).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")));
        assertEquals(7, new LevenshteinDistance(7).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")));
        assertEquals(-1, new LevenshteinDistance(6).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")));
        // unequal strings, zero threshold
        assertEquals(-1, new LevenshteinDistance(0).apply(SimilarityInputTest.build(cls, "b"), SimilarityInputTest.build(cls, "a")));
        assertEquals(-1, new LevenshteinDistance(0).apply(SimilarityInputTest.build(cls, "a"), SimilarityInputTest.build(cls, "b")));
        // equal strings
        assertEquals(0, new LevenshteinDistance(0).apply(SimilarityInputTest.build(cls, "aa"), SimilarityInputTest.build(cls, "aa")));
        assertEquals(0, new LevenshteinDistance(2).apply(SimilarityInputTest.build(cls, "aa"), SimilarityInputTest.build(cls, "aa")));
        // same length
        assertEquals(-1, new LevenshteinDistance(2).apply(SimilarityInputTest.build(cls, "aaa"), SimilarityInputTest.build(cls, "bbb")));
        assertEquals(3, new LevenshteinDistance(3).apply(SimilarityInputTest.build(cls, "aaa"), SimilarityInputTest.build(cls, "bbb")));
        // big stripe
        assertEquals(6, new LevenshteinDistance(10).apply(SimilarityInputTest.build(cls, "aaaaaa"), SimilarityInputTest.build(cls, "b")));
        // distance less than threshold
        assertEquals(7, new LevenshteinDistance(8).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "b")));
        assertEquals(3, new LevenshteinDistance(4).apply(SimilarityInputTest.build(cls, "a"), SimilarityInputTest.build(cls, "bbb")));
        // distance equal to threshold
        assertEquals(7, new LevenshteinDistance(7).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "b")));
        assertEquals(3, new LevenshteinDistance(3).apply(SimilarityInputTest.build(cls, "a"), SimilarityInputTest.build(cls, "bbb")));
        // distance greater than threshold
        assertEquals(-1, new LevenshteinDistance(2).apply(SimilarityInputTest.build(cls, "a"), SimilarityInputTest.build(cls, "bbb")));
        assertEquals(-1, new LevenshteinDistance(2).apply(SimilarityInputTest.build(cls, "bbb"), SimilarityInputTest.build(cls, "a")));
        assertEquals(-1, new LevenshteinDistance(6).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "b")));
        // stripe runs off array, strings not similar
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "a"), SimilarityInputTest.build(cls, "bbb")));
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "bbb"), SimilarityInputTest.build(cls, "a")));
        // stripe runs off array, strings are similar
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "12345"), SimilarityInputTest.build(cls, "1234567")));
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "1234567"), SimilarityInputTest.build(cls, "12345")));
        // old getLevenshteinDistance test cases
        assertEquals(1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")));
        assertEquals(3, new LevenshteinDistance(3).apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant")));
        assertEquals(7, new LevenshteinDistance(7).apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(-1, new LevenshteinDistance(6).apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(7, new LevenshteinDistance(7).apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "elephant")));
        assertEquals(-1, new LevenshteinDistance(6).apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "elephant")));
        assertEquals(8, new LevenshteinDistance(8).apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "zzzzzzzz")));
        assertEquals(8, new LevenshteinDistance(8).apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo")));
        assertEquals(1, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")));
        assertEquals(3, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant")));
        assertEquals(7, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(7, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "elephant")));
        assertEquals(8, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "zzzzzzzz")));
        assertEquals(8, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(1, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo")));
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "abc"), SimilarityInputTest.build(cls, "acb")));
    }
}
