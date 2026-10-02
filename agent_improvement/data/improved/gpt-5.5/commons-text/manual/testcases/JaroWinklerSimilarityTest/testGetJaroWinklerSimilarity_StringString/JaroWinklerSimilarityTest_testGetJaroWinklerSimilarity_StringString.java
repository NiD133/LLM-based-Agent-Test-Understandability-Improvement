package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_StringString {

    private static final double DEFAULT_DELTA = 0.00001d;
    private static final double EXACT_ZERO_DELTA = 0.00000000000000000001d;

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

    private static void assertSimilarity(final double expected, final String left, final String right) {
        assertSimilarity(expected, left, right, DEFAULT_DELTA);
    }

    private static void assertSimilarity(final double expected, final String left, final String right, final double delta) {
        assertEquals(expected, similarity.apply(wrap(left), right), delta);
    }

    @Test
    void testGetJaroWinklerSimilarity_StringString() {
        assertSimilarity(1d, "", "");
        assertSimilarity(1d, "foo", "foo");
        assertSimilarity(0.94166d, "foo", "foo ");
        assertSimilarity(0.90666d, "foo", "foo  ");
        assertSimilarity(0.86666d, "foo", " foo ");
        assertSimilarity(0.51111d, "foo", "  foo");
        assertSimilarity(0.92499d, "frog", "fog");
        assertSimilarity(0.0d, "fly", "ant", EXACT_ZERO_DELTA);
        assertSimilarity(0.44166d, "elephant", "hippo");
        assertSimilarity(0.90666d, "ABC Corporation", "ABC Corp");
        assertSimilarity(0.95251d, "D N H Enterprises Inc", "D & H Enterprises, Inc.");
        assertSimilarity(0.942d, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness");
        assertSimilarity(0.898018d, "PENNSYLVANIA", "PENNCISYLVNIA");
        assertSimilarity(0.971428d, "/opt/software1", "/opt/software2");
        assertSimilarity(0.941666d, "aaabcd", "aaacdb");
        assertSimilarity(0.911111d, "John Horn", "John Hopkins");
    }
}
