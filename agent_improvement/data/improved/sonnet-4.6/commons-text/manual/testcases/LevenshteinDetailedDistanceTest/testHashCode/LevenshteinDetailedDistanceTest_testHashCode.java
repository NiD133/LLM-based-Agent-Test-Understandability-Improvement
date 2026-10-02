package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testHashCode {

    @Test
    @DisplayName("LevenshteinResults.hashCode() equals hashCode of an equivalent result object")
    void testHashCode() {
        final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();

        // All edits are deletions: "aaapppp" → "" requires 7 deletes
        LevenshteinResults actualResult = distance.apply("aaapppp", "");
        LevenshteinResults expectedResult = new LevenshteinResults(7, 0, 7, 0);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());

        // Single deletion: "frog" → "fog" requires 1 delete
        actualResult = distance.apply("frog", "fog");
        expectedResult = new LevenshteinResults(1, 0, 1, 0);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());

        // Mixed operations: "elephant" → "hippo" requires 3 inserts and 4 deletes
        actualResult = distance.apply("elephant", "hippo");
        expectedResult = new LevenshteinResults(7, 0, 3, 4);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());
    }
}
