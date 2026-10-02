package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats the anglicised and original Gaelic spellings of the surname "O'Sullivan"
 * as a phonetic match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_OSULLIVAN_OSUILLEABHAIN_SuccessfulMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    /** The anglicised spelling of the surname. */
    private static final String ANGLICISED_SURNAME = "O'Sullivan";

    /** The original Gaelic spelling of the same surname. */
    private static final String GAELIC_SURNAME = "Ó ' Súilleabháin";

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The two spellings are phonetically equivalent, so the Match Rating Approach
     * encoder should report them as equal.
     */
    @Test
    final void testCompare_Surname_OSULLIVAN_OSUILLEABHAIN_SuccessfulMatch() {
        final boolean spellingsMatch =
                getStringEncoder().isEncodeEquals(ANGLICISED_SURNAME, GAELIC_SURNAME);

        assertTrue(spellingsMatch,
                "Anglicised and Gaelic spellings of the surname should be a phonetic match");
    }
}
