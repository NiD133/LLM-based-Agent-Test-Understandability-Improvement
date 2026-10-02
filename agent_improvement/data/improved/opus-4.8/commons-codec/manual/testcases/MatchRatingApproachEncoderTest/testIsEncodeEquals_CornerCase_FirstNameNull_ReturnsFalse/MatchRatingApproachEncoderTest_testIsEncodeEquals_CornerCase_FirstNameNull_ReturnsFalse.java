package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} for the
 * corner case where the first name is {@code null}.
 */
public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameNull_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A {@code null} first name cannot be compared, so the two names are never
     * considered homophonous and {@code isEncodeEquals} must return {@code false}.
     */
    @Test
    final void testIsEncodeEqualsReturnsFalseWhenFirstNameIsNull() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final String nullFirstName = null;
        final String secondName = "test";

        final boolean namesAreEqual = encoder.isEncodeEquals(nullFirstName, secondName);

        assertFalse(namesAreEqual, "A null first name should never match another name");
    }
}
