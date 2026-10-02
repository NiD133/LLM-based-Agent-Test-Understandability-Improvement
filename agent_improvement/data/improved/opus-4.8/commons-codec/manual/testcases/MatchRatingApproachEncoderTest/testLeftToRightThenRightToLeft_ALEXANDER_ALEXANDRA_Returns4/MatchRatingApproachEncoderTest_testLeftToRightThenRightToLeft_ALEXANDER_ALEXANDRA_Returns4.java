package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#leftToRightThenRightToLeftProcessing(String, String)}.
 *
 * <p>This method compares two encoded names by cancelling out identical characters from both
 * ends, then returns {@code 6 - (length of the longer leftover string)} as a similarity count.</p>
 */
public class MatchRatingApproachEncoderTest_testLeftToRightThenRightToLeft_ALEXANDER_ALEXANDRA_Returns4
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "ALEXANDER" and "ALEXANDRA" share enough characters that, after the
     * left-to-right then right-to-left cancellation, the longer leftover string
     * has length 2, so the similarity count is {@code 6 - 2 = 4}.
     */
    @Test
    final void leftToRightThenRightToLeftProcessing_forAlexanderAndAlexandra_returnsSimilarityOf4() {
        final String firstName = "ALEXANDER";
        final String secondName = "ALEXANDRA";
        final int expectedSimilarity = 4;

        final int actualSimilarity =
                getStringEncoder().leftToRightThenRightToLeftProcessing(firstName, secondName);

        assertEquals(expectedSimilarity, actualSimilarity);
    }
}
