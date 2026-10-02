package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testEquals {

    private static final String EMPTY = "";
    private static final String HALLO = "hallo";
    private static final String HELLO = "hello";
    private static final String HIPPO = "hippo";
    private static final String ZZZZZZZZ = "zzzzzzzz";

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testEquals(final Class<?> inputType) {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        LevenshteinResults actualResult = classBeingTested.apply(
                SimilarityInputTest.build(inputType, HELLO),
                SimilarityInputTest.build(inputType, HALLO));
        LevenshteinResults expectedResult = new LevenshteinResults(1, 0, 0, 1);
        assertEquals(expectedResult, actualResult);

        assertEquals(
                classBeingTested.apply(ZZZZZZZZ, HIPPO),
                classBeingTested.apply(
                        SimilarityInputTest.build(inputType, ZZZZZZZZ),
                        SimilarityInputTest.build(inputType, HIPPO)));

        actualResult = classBeingTested.apply(
                SimilarityInputTest.build(inputType, ZZZZZZZZ),
                SimilarityInputTest.build(inputType, HIPPO));
        expectedResult = new LevenshteinResults(8, 0, 3, 5);
        assertEquals(expectedResult, actualResult);

        assertEquals(actualResult, actualResult);

        actualResult = classBeingTested.apply(
                SimilarityInputTest.build(inputType, EMPTY),
                SimilarityInputTest.build(inputType, EMPTY));
        expectedResult = new LevenshteinResults(0, 0, 0, 0);
        assertEquals(expectedResult, actualResult);
    }
}
