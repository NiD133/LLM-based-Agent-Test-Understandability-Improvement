package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) encoder correctly determines that
 * the surnames "Murphy" and "Lynch" are phonetically dissimilar.
 *
 * MRA encodes each name by removing vowels (except word-initial), collapsing double
 * consonants, and retaining at most the first and last 3 consonants. It then checks
 * whether the two encodings are close enough (by a position-matching score) to be
 * considered the same name. "Murphy" encodes to "MRPH" and "Lynch" encodes to "LNC",
 * which are too different in both length and consonant pattern to reach the minimum
 * similarity rating — so the comparison must return {@code false}.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surnames_MURPHY_LYNCH_NoMatchExpected
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surnames_MURPHY_LYNCH_NoMatchExpected() {
        // "Murphy" and "Lynch" are phonetically distinct surnames; MRA should not consider them a match.
        assertFalse(getStringEncoder().isEncodeEquals("Murphy", "Lynch"));
    }
}
