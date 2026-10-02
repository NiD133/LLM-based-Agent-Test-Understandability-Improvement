package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testDifference {

    private static final DifferenceCase[] DIFFERENCE_CASES = {
        new DifferenceCase("Both inputs are null", null, null, 0),
        new DifferenceCase("Both inputs are empty", "", "", 0),
        new DifferenceCase("Both inputs contain one space", " ", " ", 0),
        new DifferenceCase("Smith and Smythe", "Smith", "Smythe", 6),
        new DifferenceCase("Ann and Andrew", "Ann", "Andrew", 3),
        new DifferenceCase("Margaret and Andrew", "Margaret", "Andrew", 1),
        new DifferenceCase("Janet and Margaret", "Janet", "Margaret", 1),
        new DifferenceCase("Green and Greene", "Green", "Greene", 5),
        new DifferenceCase("Blotchet-Halls and Greene", "Blotchet-Halls", "Greene", 1),
        new DifferenceCase("Smith and Smythe T-SQL example", "Smith", "Smythe", 6),
        new DifferenceCase("Smithers and Smythers", "Smithers", "Smythers", 8),
        new DifferenceCase("Anothers and Brothers", "Anothers", "Brothers", 5),
    };

    @Test
    void testDifference() throws EncoderException {
        final RefinedSoundex refinedSoundex = new RefinedSoundex();

        for (final DifferenceCase differenceCase : DIFFERENCE_CASES) {
            assertEquals(
                differenceCase.expectedDifference,
                refinedSoundex.difference(differenceCase.left, differenceCase.right),
                differenceCase.description);
        }
    }

    private static final class DifferenceCase {

        private final String description;
        private final String left;
        private final String right;
        private final int expectedDifference;

        private DifferenceCase(
            final String description,
            final String left,
            final String right,
            final int expectedDifference) {
            this.description = description;
            this.left = left;
            this.right = right;
            this.expectedDifference = expectedDifference;
        }
    }
}
