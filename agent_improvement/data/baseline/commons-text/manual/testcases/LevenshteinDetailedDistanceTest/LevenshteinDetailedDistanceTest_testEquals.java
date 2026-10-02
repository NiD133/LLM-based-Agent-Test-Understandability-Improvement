package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testEquals {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testEquals(final Class<?> cls) {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();
        LevenshteinResults actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo"));
        LevenshteinResults expectedResult = new LevenshteinResults(1, 0, 0, 1);
        assertEquals(expectedResult, actualResult);
        assertEquals(classBeingTested.apply("zzzzzzzz", "hippo"), classBeingTested.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")));
        actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo"));
        expectedResult = new LevenshteinResults(8, 0, 3, 5);
        assertEquals(expectedResult, actualResult);
        // intentionally added
        assertEquals(actualResult, actualResult);
        actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, ""));
        expectedResult = new LevenshteinResults(0, 0, 0, 0);
        assertEquals(expectedResult, actualResult);
    }
}
