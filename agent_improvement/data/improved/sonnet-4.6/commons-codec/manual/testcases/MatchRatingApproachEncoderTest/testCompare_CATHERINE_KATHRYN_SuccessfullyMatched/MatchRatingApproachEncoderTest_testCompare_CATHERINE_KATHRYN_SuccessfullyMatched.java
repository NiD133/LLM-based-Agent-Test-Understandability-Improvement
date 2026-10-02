package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) algorithm correctly identifies
 * phonetically similar names as matching, even when their spellings differ.
 *
 * The MRA algorithm works by:
 *   1. Cleaning the name (uppercase, remove punctuation/accents/spaces)
 *   2. Removing vowels (except when the name starts with one)
 *   3. Collapsing double consonants to a single consonant
 *   4. Keeping only the first 3 and last 3 letters (for names longer than 6 chars)
 *   5. Comparing the resulting codes with a minimum rating threshold
 *
 * "Catherine" encodes to "KTHRN" and "Kathryn" encodes to "KTHRN",
 * so the two names share the same MRA code and are considered a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_CATHERINE_KATHRYN_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_CATHERINE_KATHRYN_SuccessfullyMatched() {
        // "Catherine" and "Kathryn" are phonetically equivalent spellings of the same name;
        // the MRA algorithm should recognise them as a match.
        boolean namesMatch = getStringEncoder().isEncodeEquals("Catherine", "Kathryn");
        assertTrue(namesMatch);
    }
}
