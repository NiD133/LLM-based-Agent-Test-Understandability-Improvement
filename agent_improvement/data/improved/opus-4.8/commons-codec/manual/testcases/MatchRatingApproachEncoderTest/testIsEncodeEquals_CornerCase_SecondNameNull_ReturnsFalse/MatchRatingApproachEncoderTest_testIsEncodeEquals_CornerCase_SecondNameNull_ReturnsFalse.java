package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the null-handling contract of
 * {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}.
 */
public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameNull_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * When the second name is {@code null}, the two names cannot be compared,
     * so {@code isEncodeEquals} must report them as not equal (returns {@code false}).
     */
    @Test
    final void testIsEncodeEquals_CornerCase_SecondNameNull_ReturnsFalse() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final String firstName = "test";
        final String secondName = null;

        final boolean namesAreEqual = encoder.isEncodeEquals(firstName, secondName);

        assertFalse(namesAreEqual, "A null second name should never be considered equal to another name");
    }
}
