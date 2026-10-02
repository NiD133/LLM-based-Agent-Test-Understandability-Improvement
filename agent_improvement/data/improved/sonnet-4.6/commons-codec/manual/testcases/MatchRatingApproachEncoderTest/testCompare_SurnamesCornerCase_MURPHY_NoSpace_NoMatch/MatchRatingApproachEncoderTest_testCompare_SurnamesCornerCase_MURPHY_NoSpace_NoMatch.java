package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests that the MRA encoder correctly rejects a comparison where the second
 * name is an empty string, since an empty input is not a valid surname and
 * must never match anything.
 */
public class MatchRatingApproachEncoderTest_testCompare_SurnamesCornerCase_MURPHY_NoSpace_NoMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    @DisplayName("isEncodeEquals returns false when second surname is empty (no valid name to compare against)")
    final void testCompare_SurnamesCornerCase_MURPHY_NoSpace_NoMatch() {
        // An empty string is not a valid name; the MRA algorithm must return false
        // rather than treating it as a phonetic match for "Murphy".
        assertFalse(getStringEncoder().isEncodeEquals("Murphy", ""));
    }
}
