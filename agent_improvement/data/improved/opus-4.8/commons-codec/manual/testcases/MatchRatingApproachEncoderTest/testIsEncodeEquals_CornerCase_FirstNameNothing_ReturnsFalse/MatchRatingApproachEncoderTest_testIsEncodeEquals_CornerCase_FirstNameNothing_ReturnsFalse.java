package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} for the corner
 * case where the first name is empty.
 *
 * <p>{@code isEncodeEquals} treats an empty string as trivial input that cannot be matched,
 * so any comparison involving an empty first name must report the two names as not equal,
 * regardless of the second name.</p>
 */
public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameNothing_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameNothing_ReturnsFalse() {
        final String emptyFirstName = "";
        final String secondName = "test";

        final boolean namesAreEqual =
                getStringEncoder().isEncodeEquals(emptyFirstName, secondName);

        assertFalse(namesAreEqual,
                "An empty first name should never be considered equal to another name");
    }
}
