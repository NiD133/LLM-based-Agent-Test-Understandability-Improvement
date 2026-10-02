package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the corner-case behavior of
 * {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} when one of
 * the supplied names is blank.
 */
public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameJustSpace_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * When the second name is just a single space (blank), the two names cannot
     * be considered homophonous, so {@code isEncodeEquals} must return
     * {@code false} regardless of the first name.
     */
    @Test
    final void isEncodeEqualsReturnsFalseWhenSecondNameIsJustSpace() {
        final String validName = "test";
        final String blankSecondName = " ";

        final boolean namesAreHomophonous =
                getStringEncoder().isEncodeEquals(validName, blankSecondName);

        assertFalse(namesAreHomophonous,
                "A name compared against a blank (space-only) name must not be considered equal");
    }
}
