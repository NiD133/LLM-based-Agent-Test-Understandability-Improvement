package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link JaroWinklerSimilarity#apply(SimilarityInput, SimilarityInput)}.
 *
 * <p>
 * The test is parameterized over the different {@link SimilarityInput} backing types supplied by
 * {@code SimilarityInputTest#similarityInputsEquals()} (e.g. {@code String}, {@code StringBuilder}). For every type we
 * feed the same pairs of inputs and assert that the Jaro-Winkler score matches the expected value, proving the
 * algorithm is independent of the concrete input representation.
 * </p>
 */
public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity {

    /** Tolerance used for the published five-decimal reference scores. */
    private static final double TOLERANCE = 0.00001d;

    /** Stricter tolerance used when the expected score is exactly zero (no rounding to absorb). */
    private static final double ZERO_TOLERANCE = 0.00000000000000000001d;

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputsEquals()")
    void testGetJaroWinklerSimilarity(final Class<?> cls) {
        // Identical inputs (including the empty string) score a perfect 1.0.
        assertScore(1d, cls, "", "");
        assertScore(1d, cls, "foo", "foo");

        // Same characters, differing only by surrounding whitespace.
        assertScore(0.94166d, cls, "foo", "foo ");
        assertScore(0.90666d, cls, "foo", "foo  ");
        assertScore(0.86666d, cls, "foo", " foo ");
        assertScore(0.51111d, cls, "foo", "  foo");

        // Short words with small edits.
        assertScore(0.92499d, cls, "frog", "fog");
        assertScore(0.44166d, cls, "elephant", "hippo");

        // No characters in common -> score is exactly 0.0.
        assertScore(0.0d, ZERO_TOLERANCE, cls, "fly", "ant");

        // Realistic, longer strings (names, paths, abbreviations).
        assertScore(0.90666d, cls, "ABC Corporation", "ABC Corp");
        assertScore(0.95251d, cls, "D N H Enterprises Inc", "D & H Enterprises, Inc.");
        assertScore(0.942d, cls, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness");
        assertScore(0.898018d, cls, "PENNSYLVANIA", "PENNCISYLVNIA");
        assertScore(0.971428d, cls, "/opt/software1", "/opt/software2");
        assertScore(0.941666d, cls, "aaabcd", "aaacdb");
        assertScore(0.911111d, cls, "John Horn", "John Hopkins");
    }

    /**
     * Asserts the Jaro-Winkler score of {@code left} and {@code right} using the default {@link #TOLERANCE}.
     *
     * @param expected the expected similarity score.
     * @param cls      the concrete {@link SimilarityInput} backing type to build the inputs from.
     * @param left     the first input value.
     * @param right    the second input value.
     */
    private static void assertScore(final double expected, final Class<?> cls, final String left, final String right) {
        assertScore(expected, TOLERANCE, cls, left, right);
    }

    /**
     * Asserts the Jaro-Winkler score of {@code left} and {@code right} within the given {@code tolerance}.
     *
     * @param expected  the expected similarity score.
     * @param tolerance the allowed absolute difference from {@code expected}.
     * @param cls       the concrete {@link SimilarityInput} backing type to build the inputs from.
     * @param left      the first input value.
     * @param right     the second input value.
     */
    private static void assertScore(final double expected, final double tolerance, final Class<?> cls,
            final String left, final String right) {
        assertEquals(expected,
                similarity.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right)),
                tolerance);
    }
}
