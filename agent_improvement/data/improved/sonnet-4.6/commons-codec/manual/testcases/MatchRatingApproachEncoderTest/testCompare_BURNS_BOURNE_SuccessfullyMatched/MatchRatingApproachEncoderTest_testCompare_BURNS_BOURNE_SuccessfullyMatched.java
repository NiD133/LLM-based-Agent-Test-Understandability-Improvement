package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the Match Rating Approach (MRA) phonetic algorithm's ability to
 * identify phonetically similar names that differ only in spelling.
 *
 * The MRA algorithm encodes names by removing vowels (except word-initial),
 * collapsing double consonants, and reducing to a 6-character key, then
 * compares keys using a similarity rating threshold based on combined length.
 */
public class MatchRatingApproachEncoderTest_testCompare_BURNS_BOURNE_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Burns" and "Bourne" are phonetically similar names that differ only in
     * vowel placement and an extra 'e'. After MRA encoding both reduce to the
     * same consonant skeleton (BRN / BRN), so isEncodeEquals should return true.
     */
    @Test
    final void testCompare_BURNS_BOURNE_SuccessfullyMatched() {
        String name1 = "Burns";
        String name2 = "Bourne";

        assertTrue(
            getStringEncoder().isEncodeEquals(name1, name2),
            "\"Burns\" and \"Bourne\" are phonetically equivalent and should match under the MRA algorithm"
        );
    }
}
