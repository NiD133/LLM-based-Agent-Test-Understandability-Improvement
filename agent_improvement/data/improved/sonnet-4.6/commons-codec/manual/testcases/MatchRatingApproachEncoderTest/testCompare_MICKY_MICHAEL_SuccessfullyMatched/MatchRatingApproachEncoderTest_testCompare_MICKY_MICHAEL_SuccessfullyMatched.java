package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) encoder treats "Micky" and "Michael"
 * as phonetically equivalent. Both names share the same consonant skeleton after MRA
 * preprocessing (vowel removal, double-consonant reduction, first/last-3 extraction),
 * so the algorithm's similarity count meets the minimum rating threshold.
 */
public class MatchRatingApproachEncoderTest_testCompare_MICKY_MICHAEL_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_MICKY_MICHAEL_SuccessfullyMatched() {
        // "Micky" and "Michael" are phonetic variants of the same name; MRA should
        // recognise them as a match (isEncodeEquals returns true).
        assertTrue(getStringEncoder().isEncodeEquals("Micky", "Michael"));
    }
}
