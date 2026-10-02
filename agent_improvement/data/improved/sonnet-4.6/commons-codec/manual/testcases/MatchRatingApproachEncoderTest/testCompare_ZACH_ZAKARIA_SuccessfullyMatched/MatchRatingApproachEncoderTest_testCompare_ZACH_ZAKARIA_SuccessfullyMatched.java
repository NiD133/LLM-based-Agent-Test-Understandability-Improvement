package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) encoder recognises "Zach" and "Zacharia"
 * as phonetically equivalent. Under MRA, both names reduce to the same consonant skeleton
 * after vowel removal and double-consonant collapsing, so their similarity rating meets
 * the minimum threshold required for a positive match.
 */
public class MatchRatingApproachEncoderTest_testCompare_ZACH_ZAKARIA_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Zach" and "Zacharia" are common nickname / full-name variants of the same person's name.
     * The MRA algorithm should consider them a successful match (isEncodeEquals returns true).
     */
    @Test
    final void testCompare_ZACH_ZAKARIA_SuccessfullyMatched() {
        String nickname  = "Zach";
        String fullName  = "Zacharia";

        assertTrue(getStringEncoder().isEncodeEquals(nickname, fullName),
                "MRA should recognise \"" + nickname + "\" and \"" + fullName + "\" as phonetically equivalent");
    }
}
