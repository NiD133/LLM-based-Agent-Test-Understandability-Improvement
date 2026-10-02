package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) encoder correctly identifies
 * phonetically equivalent name variants that differ in accent marks and spelling.
 */
public class MatchRatingApproachEncoderTest_testCompare_SEAN_SHAUN_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Séan" (Irish/Gaelic spelling with an accented é) and "Shaun" (anglicised
     * phonetic spelling) are two common written forms of the same spoken name.
     * The MRA encoder must strip the accent from 'é', apply its phonetic reduction
     * rules to both strings, and conclude that the resulting codes are similar
     * enough to be considered a match.
     */
    @Test
    final void testCompare_SEAN_SHAUN_SuccessfullyMatched() {
        MatchRatingApproachEncoder encoder = getStringEncoder();

        // Both spellings represent the same spoken name and must be recognised as equivalent.
        assertTrue(encoder.isEncodeEquals("Séan", "Shaun"));
    }
}
