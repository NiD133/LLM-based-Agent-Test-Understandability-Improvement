package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @SuppressWarnings("unchecked")
    private static <E> SimilarityInput<E> input(final Class<?> cls, final String value) {
        return (SimilarityInput<E>) SimilarityInputTest.build(cls, value);
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("Unlimited Levenshtein distance computes correct edit distances")
    void testGetLevenshteinDistance(final Class<?> cls) {
        // Both inputs empty — no edits needed
        assertEquals(0, UNLIMITED_DISTANCE.apply(input(cls, ""), input(cls, "")), "empty vs empty");

        // One input empty — distance equals the length of the other
        assertEquals(1, UNLIMITED_DISTANCE.apply(input(cls, ""), input(cls, "a")), "empty vs 'a'");
        assertEquals(7, UNLIMITED_DISTANCE.apply(input(cls, "aaapppp"), input(cls, "")), "'aaapppp' vs empty");

        // Single deletion turns 'frog' into 'fog'
        assertEquals(1, UNLIMITED_DISTANCE.apply(input(cls, "frog"), input(cls, "fog")), "'frog' vs 'fog'");

        // Completely different short words require full replacement
        assertEquals(3, UNLIMITED_DISTANCE.apply(input(cls, "fly"), input(cls, "ant")), "'fly' vs 'ant'");

        // Longer words with large structural differences; distance is symmetric
        assertEquals(7, UNLIMITED_DISTANCE.apply(input(cls, "elephant"), input(cls, "hippo")), "'elephant' vs 'hippo'");
        assertEquals(7, UNLIMITED_DISTANCE.apply(input(cls, "hippo"), input(cls, "elephant")), "'hippo' vs 'elephant' (symmetry check)");

        // Length difference dominates when the other string shares no characters; symmetric
        assertEquals(8, UNLIMITED_DISTANCE.apply(input(cls, "hippo"), input(cls, "zzzzzzzz")), "'hippo' vs 'zzzzzzzz'");
        assertEquals(8, UNLIMITED_DISTANCE.apply(input(cls, "zzzzzzzz"), input(cls, "hippo")), "'zzzzzzzz' vs 'hippo' (symmetry check)");

        // Single substitution: 'e' → 'a' converts 'hello' to 'hallo'
        assertEquals(1, UNLIMITED_DISTANCE.apply(input(cls, "hello"), input(cls, "hallo")), "'hello' vs 'hallo'");
    }
}
