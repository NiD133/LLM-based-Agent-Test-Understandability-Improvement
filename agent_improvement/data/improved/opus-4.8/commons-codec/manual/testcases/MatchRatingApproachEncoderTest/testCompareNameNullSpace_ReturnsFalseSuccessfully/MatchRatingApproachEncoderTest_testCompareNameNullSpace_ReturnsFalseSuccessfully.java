package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} for the
 * "trivial input" guard: when either argument is {@code null} or a blank space,
 * the two names are never treated as homophonous.
 */
public class MatchRatingApproachEncoderTest_testCompareNameNullSpace_ReturnsFalseSuccessfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A {@code null} first name compared against a blank-space second name must not
     * be considered an encoding match.
     */
    @Test
    final void testCompareNullAgainstSpaceIsNotAMatch() {
        final String nullName = null;
        final String spaceName = " ";

        final boolean namesMatch = getStringEncoder().isEncodeEquals(nullName, spaceName);

        assertFalse(namesMatch, "null and a blank space must not be reported as a match");
    }
}
