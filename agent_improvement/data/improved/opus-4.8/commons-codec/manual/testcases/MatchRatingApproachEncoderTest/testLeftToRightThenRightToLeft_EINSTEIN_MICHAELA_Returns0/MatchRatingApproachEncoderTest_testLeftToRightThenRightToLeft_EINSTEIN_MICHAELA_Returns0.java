package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#leftToRightThenRightToLeftProcessing(String, String)}.
 *
 * <p>The method scans the two names from both ends, blanks out characters that match in the
 * same position, then returns {@code |6 - length-of-the-longer-remaining-string|}. For the
 * 8-letter names "EINSTEIN" and "MICHAELA" two positions cancel out (an "I" and an "E"),
 * leaving "ENSTIN" and "MCHALA" — both 6 characters long. The result is therefore
 * {@code |6 - 6| == 0}.</p>
 */
public class MatchRatingApproachEncoderTest_testLeftToRightThenRightToLeft_EINSTEIN_MICHAELA_Returns0
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void leftToRightThenRightToLeftProcessing_forEinsteinAndMichaela_returnsZero() {
        final String firstName = "EINSTEIN";
        final String secondName = "MICHAELA";
        final int expectedSimilarityRating = 0;

        final int actualSimilarityRating =
                getStringEncoder().leftToRightThenRightToLeftProcessing(firstName, secondName);

        assertEquals(expectedSimilarityRating, actualSimilarityRating);
    }
}
