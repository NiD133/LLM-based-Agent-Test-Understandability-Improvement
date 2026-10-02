package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the Match Rating Approach (MRA) phonetic comparison algorithm for names
 * that sound alike but differ in spelling. The MRA algorithm encodes names by
 * removing vowels (except at word start), collapsing double consonants, and
 * retaining the first and last three letters before comparing similarity ratings.
 */
public class MatchRatingApproachEncoderTest_testCompare_OONA_OONAGH_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Oona" and "Oonagh" are phonetic variants of the same Irish name.
     * The MRA algorithm should recognise them as equivalent because, after
     * encoding, their similarity rating meets the minimum threshold required
     * for a successful match.
     */
    @Test
    final void testCompare_OONA_OONAGH_SuccessfullyMatched() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        assertTrue(encoder.isEncodeEquals("Oona", "Oonagh"));
    }
}
