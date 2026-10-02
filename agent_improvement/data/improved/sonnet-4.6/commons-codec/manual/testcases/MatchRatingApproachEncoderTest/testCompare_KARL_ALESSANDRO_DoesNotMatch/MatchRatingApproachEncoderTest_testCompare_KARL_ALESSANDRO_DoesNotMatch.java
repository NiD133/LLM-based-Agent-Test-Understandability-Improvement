package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) encoder correctly identifies
 * "Karl" and "Alessandro" as phonetically dissimilar names. Despite both
 * starting with vowel-adjacent consonants, their encoded forms differ
 * significantly in length (KRL vs ALSNDR), causing the algorithm to reject
 * a match before even reaching the rating comparison step.
 */
public class MatchRatingApproachEncoderTest_testCompare_KARL_ALESSANDRO_DoesNotMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Karl" encodes to a short consonant skeleton (KRL) while "Alessandro"
     * encodes to a much longer one (ALSNDR). The MRA algorithm rejects pairs
     * whose encoded forms differ in length by 3 or more characters, so these
     * two names must NOT be considered a match.
     */
    @Test
    final void testCompare_KARL_ALESSANDRO_DoesNotMatch() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        boolean result = encoder.isEncodeEquals("Karl", "Alessandro");
        assertFalse(result,
                "\"Karl\" and \"Alessandro\" have very different phonetic encodings and must not match under MRA");
    }
}
