package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link JaroWinklerSimilarity#apply(CharSequence, CharSequence)} for a range of input pairs,
 * checking that the computed similarity score matches the expected value.
 */
public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_StringString {

    /** Tolerance used when comparing the (floating-point) similarity scores. */
    private static final double DEFAULT_TOLERANCE = 0.00001d;

    /** Stricter tolerance used for the "no characters in common" case, whose exact score is 0.0. */
    private static final double EXACT_ZERO_TOLERANCE = 0.00000000000000000001d;

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    /**
     * Wraps the string in a custom {@link CharSequence}. This ensures that using the {@link Object#equals(Object)} method on the input CharSequence to test for
     * equality will fail.
     *
     * @param string the string
     * @return the char sequence
     */
    private static CharSequence wrap(final String string) {
        return new CharSequence() {

            @Override
            public char charAt(final int index) {
                return string.charAt(index);
            }

            @Override
            public boolean equals(final Object obj) {
                return string.equals(obj);
            }

            @Override
            public int hashCode() {
                return string.hashCode();
            }

            @Override
            public int length() {
                return string.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return string.subSequence(start, end);
            }

            @Override
            public String toString() {
                return string;
            }
        };
    }

    /**
     * Supplies the (left input, right input, expected score, comparison tolerance) tuples exercised by
     * {@link #testGetJaroWinklerSimilarity_StringString(String, String, double, double)}.
     */
    private static Stream<Arguments> similarityCases() {
        return Stream.of(
            Arguments.of("", "", 1d, DEFAULT_TOLERANCE),
            Arguments.of("foo", "foo", 1d, DEFAULT_TOLERANCE),
            Arguments.of("foo", "foo ", 0.94166d, DEFAULT_TOLERANCE),
            Arguments.of("foo", "foo  ", 0.90666d, DEFAULT_TOLERANCE),
            Arguments.of("foo", " foo ", 0.86666d, DEFAULT_TOLERANCE),
            Arguments.of("foo", "  foo", 0.51111d, DEFAULT_TOLERANCE),
            Arguments.of("frog", "fog", 0.92499d, DEFAULT_TOLERANCE),
            Arguments.of("fly", "ant", 0.0d, EXACT_ZERO_TOLERANCE),
            Arguments.of("elephant", "hippo", 0.44166d, DEFAULT_TOLERANCE),
            Arguments.of("ABC Corporation", "ABC Corp", 0.90666d, DEFAULT_TOLERANCE),
            Arguments.of("D N H Enterprises Inc", "D & H Enterprises, Inc.", 0.95251d, DEFAULT_TOLERANCE),
            Arguments.of("My Gym Children's Fitness Center", "My Gym. Childrens Fitness", 0.942d, DEFAULT_TOLERANCE),
            Arguments.of("PENNSYLVANIA", "PENNCISYLVNIA", 0.898018d, DEFAULT_TOLERANCE),
            Arguments.of("/opt/software1", "/opt/software2", 0.971428d, DEFAULT_TOLERANCE),
            Arguments.of("aaabcd", "aaacdb", 0.941666d, DEFAULT_TOLERANCE),
            Arguments.of("John Horn", "John Hopkins", 0.911111d, DEFAULT_TOLERANCE));
    }

    @ParameterizedTest(name = "apply(\"{0}\", \"{1}\") = {2}")
    @MethodSource("similarityCases")
    void testGetJaroWinklerSimilarity_StringString(final String left, final String right,
            final double expectedScore, final double tolerance) {
        assertEquals(expectedScore, similarity.apply(wrap(left), right), tolerance);
    }
}
