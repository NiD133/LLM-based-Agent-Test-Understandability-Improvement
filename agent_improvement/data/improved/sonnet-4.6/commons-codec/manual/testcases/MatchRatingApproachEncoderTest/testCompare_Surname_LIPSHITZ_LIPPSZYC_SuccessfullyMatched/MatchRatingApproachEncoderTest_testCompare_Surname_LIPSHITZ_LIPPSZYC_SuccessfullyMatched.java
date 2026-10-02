package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_LIPSHITZ_LIPPSZYC_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surname_LIPSHITZ_LIPPSZYC_SuccessfullyMatched() {
        // "LIPSHITZ" and "LIPPSZYC" are phonetically similar surname variants;
        // the Match Rating Approach algorithm should recognise them as a match.
        MatchRatingApproachEncoder encoder = getStringEncoder();
        boolean namesMatch = encoder.isEncodeEquals("LIPSHITZ", "LIPPSZYC");
        assertTrue(namesMatch, "Expected LIPSHITZ and LIPPSZYC to be considered phonetically equivalent by the MRA algorithm");
    }
}
