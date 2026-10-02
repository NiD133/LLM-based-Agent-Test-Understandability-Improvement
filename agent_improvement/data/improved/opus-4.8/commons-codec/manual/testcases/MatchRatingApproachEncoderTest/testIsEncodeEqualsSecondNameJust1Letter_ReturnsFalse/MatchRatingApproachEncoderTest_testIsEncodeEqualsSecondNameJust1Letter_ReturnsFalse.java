package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * rejects a comparison when one of the two names consists of a single letter.
 *
 * <p>The Match Rating Approach algorithm treats single-letter names as too short
 * to compare, so {@code isEncodeEquals} short-circuits and returns {@code false}
 * regardless of the other name.</p>
 */
public class MatchRatingApproachEncoderTest_testIsEncodeEqualsSecondNameJust1Letter_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsEncodeEqualsSecondNameJust1Letter_ReturnsFalse() {
        // Arrange: a normal name compared against a single-letter second name.
        final String multiLetterName = "test";
        final String singleLetterName = "t";

        // Act
        final boolean namesMatch =
                getStringEncoder().isEncodeEquals(multiLetterName, singleLetterName);

        // Assert: a single-letter second name is never considered a match.
        assertFalse(namesMatch,
                "A single-letter second name should not be treated as encode-equal");
    }
}
