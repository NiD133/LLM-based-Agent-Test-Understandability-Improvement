package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_LEWINSKY_LEVINSKI_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that the Match Rating Approach (MRA) algorithm recognises "LEWINSKY" and "LEVINSKI"
     * as phonetically equivalent surname variants. Both names share the same consonant skeleton
     * (L-W/V-N-S-K) after MRA preprocessing (vowel removal, double-consonant reduction, and
     * first3/last3 extraction), so their similarity rating must meet or exceed the minimum
     * threshold required by the algorithm.
     */
    @Test
    final void testCompare_Surname_LEWINSKY_LEVINSKI_SuccessfullyMatched() {
        MatchRatingApproachEncoder encoder = getStringEncoder();

        // "LEWINSKY" and "LEVINSKI" are phonetic spelling variants of the same surname.
        // The MRA algorithm should report them as a matching pair.
        boolean phoneticallySimilar = encoder.isEncodeEquals("LEWINSKY", "LEVINSKI");

        assertTrue(phoneticallySimilar,
                "MRA algorithm should consider 'LEWINSKY' and 'LEVINSKI' as phonetically matching surnames");
    }
}
