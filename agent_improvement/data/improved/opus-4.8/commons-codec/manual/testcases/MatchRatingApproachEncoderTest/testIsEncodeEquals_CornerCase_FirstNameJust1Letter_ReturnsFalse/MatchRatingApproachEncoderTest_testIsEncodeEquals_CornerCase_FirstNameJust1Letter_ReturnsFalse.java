package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the corner-case behaviour of
 * {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} when one of
 * the two names is only a single letter long.
 *
 * <p>The Match Rating Approach algorithm cannot meaningfully compare a name that
 * is just one letter, so {@code isEncodeEquals} short-circuits and reports the
 * two names as <em>not</em> equal, regardless of the other name's value.</p>
 */
public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameJust1Letter_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void singleLetterFirstNameIsNeverEqualToLongerName() {
        final String singleLetterName = "t";
        final String multiLetterName = "test";

        final boolean namesConsideredEqual =
                getStringEncoder().isEncodeEquals(singleLetterName, multiLetterName);

        assertFalse(namesConsideredEqual,
                "A single-letter first name must never be reported as equal to a longer name");
    }
}
