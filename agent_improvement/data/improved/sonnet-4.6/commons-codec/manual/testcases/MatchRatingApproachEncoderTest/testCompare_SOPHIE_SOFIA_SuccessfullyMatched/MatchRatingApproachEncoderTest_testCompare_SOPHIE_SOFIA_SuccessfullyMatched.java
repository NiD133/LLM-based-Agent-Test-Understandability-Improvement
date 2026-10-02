package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) algorithm considers "Sophie" and "Sofia"
 * phonetically equivalent. Both names reduce to the same consonant skeleton after the
 * MRA preprocessing steps (remove vowels, collapse double consonants, keep first/last 3
 * letters), so isEncodeEquals must return true.
 */
public class MatchRatingApproachEncoderTest_testCompare_SOPHIE_SOFIA_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_SOPHIE_SOFIA_SuccessfullyMatched() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        String name1 = "Sophie";
        String name2 = "Sofia";

        // Despite the different spellings, both names share the same phonetic structure
        // under the MRA algorithm, so they should be considered a match.
        assertTrue(encoder.isEncodeEquals(name1, name2));
    }
}
