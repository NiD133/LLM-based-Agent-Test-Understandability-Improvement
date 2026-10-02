package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) algorithm correctly identifies
 * phonetically similar name variants as matching.
 */
public class MatchRatingApproachEncoderTest_testCompare_STEPHEN_STEVEN_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Stephen" and "Steven" are alternate spellings of the same name and should
     * be recognized as phonetically equivalent by the MRA algorithm.
     */
    @Test
    final void testCompare_STEPHEN_STEVEN_SuccessfullyMatched() {
        // Arrange
        MatchRatingApproachEncoder encoder = getStringEncoder();
        String name1 = "Stephen";
        String name2 = "Steven";

        // Act
        boolean arePhoneticallySimilar = encoder.isEncodeEquals(name1, name2);

        // Assert: alternate spellings of the same name should match
        assertTrue(arePhoneticallySimilar);
    }
}
