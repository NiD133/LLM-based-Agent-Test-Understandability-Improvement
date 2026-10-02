package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testToString {

    @Test
    void testToString() {
        final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();

        // "fly" → "ant": 3 substitutions, no insertions or deletions
        // LevenshteinResults(totalDistance, insertions, deletions, substitutions)
        LevenshteinResults flyToAnt = distance.apply("fly", "ant");
        assertEquals(new LevenshteinResults(3, 0, 0, 3).toString(), flyToAnt.toString());

        // "hippo" → "elephant": distance 7 (3 insertions, 0 deletions, 4 substitutions)
        LevenshteinResults hippoToElephant = distance.apply("hippo", "elephant");
        assertEquals(new LevenshteinResults(7, 3, 0, 4).toString(), hippoToElephant.toString());

        // "" → "a": distance 1 (1 insertion, no deletions or substitutions)
        LevenshteinResults emptyToA = distance.apply("", "a");
        assertEquals(new LevenshteinResults(1, 1, 0, 0).toString(), emptyToA.toString());
    }
}
