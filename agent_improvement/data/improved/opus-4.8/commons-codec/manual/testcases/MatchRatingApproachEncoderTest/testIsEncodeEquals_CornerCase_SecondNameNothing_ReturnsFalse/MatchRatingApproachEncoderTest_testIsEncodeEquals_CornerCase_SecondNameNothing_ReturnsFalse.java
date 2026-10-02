package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} for the corner case
 * where the second name is an empty string.
 */
public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameNothing_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * When the second name is empty, the two names cannot be homophonous, so
     * {@code isEncodeEquals} must return {@code false}.
     */
    @Test
    final void isEncodeEqualsReturnsFalseWhenSecondNameIsEmpty() {
        final String firstName = "test";
        final String emptySecondName = "";

        final boolean encodingsAreEqual =
                getStringEncoder().isEncodeEquals(firstName, emptySecondName);

        assertFalse(encodingsAreEqual,
                "An empty second name should never be considered homophonous with another name");
    }
}
