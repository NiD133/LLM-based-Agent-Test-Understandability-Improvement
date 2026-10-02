package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity {

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

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputsEquals()")
    void testGetJaroWinklerSimilarity(final Class<?> cls) {
        assertEquals(1d, similarity.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "")), 0.00001d);
        assertEquals(1d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo")), 0.00001d);
        assertEquals(0.94166d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo ")), 0.00001d);
        assertEquals(0.90666d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo  ")), 0.00001d);
        assertEquals(0.86666d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, " foo ")), 0.00001d);
        assertEquals(0.51111d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "  foo")), 0.00001d);
        assertEquals(0.92499d, similarity.apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")), 0.00001d);
        assertEquals(0.0d, similarity.apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant")), 0.00000000000000000001d);
        assertEquals(0.44166d, similarity.apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")), 0.00001d);
        assertEquals(0.90666d, similarity.apply(SimilarityInputTest.build(cls, "ABC Corporation"), SimilarityInputTest.build(cls, "ABC Corp")), 0.00001d);
        assertEquals(0.95251d, similarity.apply(SimilarityInputTest.build(cls, "D N H Enterprises Inc"), SimilarityInputTest.build(cls, "D & H Enterprises, Inc.")), 0.00001d);
        assertEquals(0.942d, similarity.apply(SimilarityInputTest.build(cls, "My Gym Children's Fitness Center"), SimilarityInputTest.build(cls, "My Gym. Childrens Fitness")), 0.00001d);
        assertEquals(0.898018d, similarity.apply(SimilarityInputTest.build(cls, "PENNSYLVANIA"), SimilarityInputTest.build(cls, "PENNCISYLVNIA")), 0.00001d);
        assertEquals(0.971428d, similarity.apply(SimilarityInputTest.build(cls, "/opt/software1"), SimilarityInputTest.build(cls, "/opt/software2")), 0.00001d);
        assertEquals(0.941666d, similarity.apply(SimilarityInputTest.build(cls, "aaabcd"), SimilarityInputTest.build(cls, "aaacdb")), 0.00001d);
        assertEquals(0.911111d, similarity.apply(SimilarityInputTest.build(cls, "John Horn"), SimilarityInputTest.build(cls, "John Hopkins")), 0.00001d);
    }
}
