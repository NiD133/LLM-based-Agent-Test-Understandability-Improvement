package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LevenshteinDetailedDistance} produces {@link LevenshteinResults} that
 * compare equal regardless of which {@link SimilarityInput} implementation wraps the input text,
 * and that those results honour the {@link Object#equals(Object)} contract.
 */
public class LevenshteinDetailedDistanceTest_testEquals {

    /** The unlimited (no-threshold) distance instance shared by every case. */
    private static final LevenshteinDetailedDistance DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    /**
     * Runs once per {@link SimilarityInput} implementation supplied by the shared provider so that
     * the equality assertions are exercised against each supported input type.
     *
     * @param inputType the concrete type used to wrap each input string.
     */
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testEquals(final Class<?> inputType) {
        // "hello" -> "hallo": a single substitution (1 change in total).
        assertEquals(
                new LevenshteinResults(1, 0, 0, 1),
                distanceBetween(inputType, "hello", "hallo"));

        // Wrapping the inputs in the parameterized type must yield the same result as plain Strings.
        assertEquals(
                DISTANCE.apply("zzzzzzzz", "hippo"),
                distanceBetween(inputType, "zzzzzzzz", "hippo"));

        // "zzzzzzzz" -> "hippo": 8 changes (3 deletions + 5 substitutions).
        final LevenshteinResults zToHippo = distanceBetween(inputType, "zzzzzzzz", "hippo");
        assertEquals(new LevenshteinResults(8, 0, 3, 5), zToHippo);
        // equals must be reflexive: a result is always equal to itself.
        assertEquals(zToHippo, zToHippo);

        // Two empty inputs: no changes at all.
        assertEquals(
                new LevenshteinResults(0, 0, 0, 0),
                distanceBetween(inputType, "", ""));
    }

    /**
     * Applies the shared distance to {@code left} and {@code right}, each wrapped in the given
     * {@link SimilarityInput} implementation.
     */
    private static LevenshteinResults distanceBetween(final Class<?> inputType, final String left, final String right) {
        return DISTANCE.apply(SimilarityInputTest.build(inputType, left), SimilarityInputTest.build(inputType, right));
    }
}
