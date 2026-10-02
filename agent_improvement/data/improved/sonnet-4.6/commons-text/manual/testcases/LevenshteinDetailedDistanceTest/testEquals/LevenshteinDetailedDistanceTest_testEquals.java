package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testEquals {

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testEquals(final Class<?> cls) {
        final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();

        // "hello" vs "hallo": one substitution ('e' -> 'a'), no insertions or deletions
        LevenshteinResults helloHalloResult = distance.apply(
                SimilarityInputTest.build(cls, "hello"),
                SimilarityInputTest.build(cls, "hallo"));
        LevenshteinResults helloHalloExpected = new LevenshteinResults(1, 0, 0, 1);
        assertEquals(helloHalloExpected, helloHalloResult);

        // CharSequence and SimilarityInput overloads must produce identical results for the same strings
        LevenshteinResults zzzzHippoViaCharSequence = distance.apply("zzzzzzzz", "hippo");
        LevenshteinResults zzzzHippoViaSimilarityInput = distance.apply(
                SimilarityInputTest.build(cls, "zzzzzzzz"),
                SimilarityInputTest.build(cls, "hippo"));
        assertEquals(zzzzHippoViaCharSequence, zzzzHippoViaSimilarityInput);

        // "zzzzzzzz" vs "hippo": 5 substitutions + 3 deletions = total distance 8
        LevenshteinResults zzzzHippoExpected = new LevenshteinResults(8, 0, 3, 5);
        assertEquals(zzzzHippoExpected, zzzzHippoViaSimilarityInput);

        // A result must be equal to itself (reflexive equality)
        assertEquals(zzzzHippoViaSimilarityInput, zzzzHippoViaSimilarityInput);

        // Two empty strings have zero distance with no edit operations required
        LevenshteinResults emptyEmptyResult = distance.apply(
                SimilarityInputTest.build(cls, ""),
                SimilarityInputTest.build(cls, ""));
        LevenshteinResults emptyEmptyExpected = new LevenshteinResults(0, 0, 0, 0);
        assertEquals(emptyEmptyExpected, emptyEmptyResult);
    }
}
