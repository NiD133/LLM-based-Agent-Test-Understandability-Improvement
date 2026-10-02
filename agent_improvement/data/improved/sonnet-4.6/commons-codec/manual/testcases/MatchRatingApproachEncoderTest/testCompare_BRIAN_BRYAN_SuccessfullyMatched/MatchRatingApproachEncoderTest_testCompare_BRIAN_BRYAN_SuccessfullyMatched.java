package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_BRIAN_BRYAN_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Brian" and "Bryan" are common spelling variants of the same name. After the MRA
     * encoding steps (vowel removal, double-consonant collapsing, first/last-3 trimming)
     * both reduce to phonetically equivalent codes, so isEncodeEquals must return true.
     */
    @Test
    final void testCompare_BRIAN_BRYAN_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals("Brian", "Bryan"));
    }
}
