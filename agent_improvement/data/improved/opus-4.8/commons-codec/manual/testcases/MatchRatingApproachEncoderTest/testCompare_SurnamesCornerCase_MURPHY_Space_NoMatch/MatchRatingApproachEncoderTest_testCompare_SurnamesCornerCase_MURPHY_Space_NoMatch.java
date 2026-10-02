package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies a corner case of {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}:
 * a real surname compared against a blank (single-space) string must never be reported
 * as a phonetic match.
 *
 * <p>The encoder treats a lone space as trivial input and short-circuits to {@code false}
 * before any phonetic comparison takes place, so "Murphy" and " " are expected to be unequal.</p>
 */
public class MatchRatingApproachEncoderTest_testCompare_SurnamesCornerCase_MURPHY_Space_NoMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_SurnamesCornerCase_MURPHY_Space_NoMatch() {
        final String surname = "Murphy";
        final String blank = " ";

        assertFalse(getStringEncoder().isEncodeEquals(surname, blank),
                "A surname compared against a blank string must not be a phonetic match");
    }
}
