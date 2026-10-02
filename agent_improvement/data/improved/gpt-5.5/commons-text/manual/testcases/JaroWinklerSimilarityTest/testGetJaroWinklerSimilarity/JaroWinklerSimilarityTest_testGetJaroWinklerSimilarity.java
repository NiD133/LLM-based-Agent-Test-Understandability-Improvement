package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity {

    private static final double STANDARD_TOLERANCE = 0.00001d;
    private static final double EXACT_ZERO_TOLERANCE = 0.00000000000000000001d;

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputsEquals()")
    void testGetJaroWinklerSimilarity(final Class<?> cls) {
        assertSimilarity(cls, "", "", 1d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "foo", "foo", 1d, STANDARD_TOLERANCE);

        assertSimilarity(cls, "foo", "foo ", 0.94166d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "foo", "foo  ", 0.90666d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "foo", " foo ", 0.86666d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "foo", "  foo", 0.51111d, STANDARD_TOLERANCE);

        assertSimilarity(cls, "frog", "fog", 0.92499d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "fly", "ant", 0.0d, EXACT_ZERO_TOLERANCE);
        assertSimilarity(cls, "elephant", "hippo", 0.44166d, STANDARD_TOLERANCE);

        assertSimilarity(cls, "ABC Corporation", "ABC Corp", 0.90666d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "D N H Enterprises Inc", "D & H Enterprises, Inc.", 0.95251d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness", 0.942d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "PENNSYLVANIA", "PENNCISYLVNIA", 0.898018d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "/opt/software1", "/opt/software2", 0.971428d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "aaabcd", "aaacdb", 0.941666d, STANDARD_TOLERANCE);
        assertSimilarity(cls, "John Horn", "John Hopkins", 0.911111d, STANDARD_TOLERANCE);
    }

    private static void assertSimilarity(final Class<?> cls, final String left, final String right, final double expected, final double tolerance) {
        assertEquals(
                expected,
                similarity.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right)),
                tolerance);
    }
}
