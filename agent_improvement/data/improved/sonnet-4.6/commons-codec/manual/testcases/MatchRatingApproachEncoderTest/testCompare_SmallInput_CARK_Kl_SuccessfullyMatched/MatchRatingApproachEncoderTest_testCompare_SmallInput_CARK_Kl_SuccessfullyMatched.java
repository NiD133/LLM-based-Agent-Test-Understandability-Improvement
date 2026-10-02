package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SmallInput_CARK_Kl_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that a short abbreviation "Kl" phonetically matches the full name "Karl"
     * under the Match Rating Approach algorithm. Both inputs are small (length < 6),
     * so they should be considered equivalent encodings.
     */
    @Test
    final void testCompare_SmallInput_CARK_Kl_SuccessfullyMatched() {
        final String abbreviation = "Kl";
        final String fullName = "Karl";
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        assertTrue(encoder.isEncodeEquals(abbreviation, fullName));
    }
}
