package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("JaroWinklerSimilarity – apply()")
public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity {

    /** Floating-point tolerance used for all similarity comparisons. */
    private static final double DELTA = 0.00001d;

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @DisplayName("Similarity scores for a variety of string pairs")
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputsEquals()")
    void testGetJaroWinklerSimilarity(final Class<?> cls) {

        // Identical strings → perfect score of 1.0
        assertEquals(1d, similarity.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "")),
                DELTA, "Two empty strings should be identical");
        assertEquals(1d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo")),
                DELTA, "Identical non-empty strings should score 1.0");

        // Trailing / leading spaces reduce the score
        assertEquals(0.94166d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo ")),
                DELTA, "One trailing space lowers the score slightly");
        assertEquals(0.90666d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo  ")),
                DELTA, "Two trailing spaces lower the score further");
        assertEquals(0.86666d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, " foo ")),
                DELTA, "Surrounding spaces lower the score more");
        assertEquals(0.51111d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "  foo")),
                DELTA, "Two leading spaces drop the score significantly");

        // Short words with minor differences
        assertEquals(0.92499d, similarity.apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")),
                DELTA, "frog vs fog – one deleted character");
        assertEquals(0.0d, similarity.apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant")),
                0.00000000000000000001d, "Completely different short words should score 0.0");
        assertEquals(0.44166d, similarity.apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")),
                DELTA, "Mostly dissimilar words with some shared characters");

        // Business / real-world strings
        assertEquals(0.90666d, similarity.apply(SimilarityInputTest.build(cls, "ABC Corporation"), SimilarityInputTest.build(cls, "ABC Corp")),
                DELTA, "Full vs abbreviated company name");
        assertEquals(0.95251d, similarity.apply(SimilarityInputTest.build(cls, "D N H Enterprises Inc"), SimilarityInputTest.build(cls, "D & H Enterprises, Inc.")),
                DELTA, "Company name with punctuation and abbreviation differences");
        assertEquals(0.942d, similarity.apply(SimilarityInputTest.build(cls, "My Gym Children's Fitness Center"), SimilarityInputTest.build(cls, "My Gym. Childrens Fitness")),
                DELTA, "Long name with apostrophe and truncation differences");

        // Longer words with internal transpositions / substitutions
        assertEquals(0.898018d, similarity.apply(SimilarityInputTest.build(cls, "PENNSYLVANIA"), SimilarityInputTest.build(cls, "PENNCISYLVNIA")),
                DELTA, "Common misspelling with transposed characters");
        assertEquals(0.971428d, similarity.apply(SimilarityInputTest.build(cls, "/opt/software1"), SimilarityInputTest.build(cls, "/opt/software2")),
                DELTA, "Paths differing only in the trailing digit");
        assertEquals(0.941666d, similarity.apply(SimilarityInputTest.build(cls, "aaabcd"), SimilarityInputTest.build(cls, "aaacdb")),
                DELTA, "Short strings with transposed characters near the end");
        assertEquals(0.911111d, similarity.apply(SimilarityInputTest.build(cls, "John Horn"), SimilarityInputTest.build(cls, "John Hopkins")),
                DELTA, "Similar surnames sharing a common prefix");
    }
}
