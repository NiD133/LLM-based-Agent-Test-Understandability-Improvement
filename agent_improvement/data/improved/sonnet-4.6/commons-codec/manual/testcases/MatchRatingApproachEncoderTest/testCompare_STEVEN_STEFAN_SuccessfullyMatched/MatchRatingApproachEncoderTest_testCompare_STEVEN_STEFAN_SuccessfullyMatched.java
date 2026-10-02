package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) encoder treats "Steven" and "Stefan"
 * as phonetically equivalent. Both names reduce to the same MRA code because they
 * share consonant structure after vowel removal and double-consonant collapsing.
 */
public class MatchRatingApproachEncoderTest_testCompare_STEVEN_STEFAN_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_STEVEN_STEFAN_SuccessfullyMatched() {
        // "Steven" and "Stefan" are phonetically similar names that MRA should match
        assertTrue(getStringEncoder().isEncodeEquals("Steven", "Stefan"));
    }
}
