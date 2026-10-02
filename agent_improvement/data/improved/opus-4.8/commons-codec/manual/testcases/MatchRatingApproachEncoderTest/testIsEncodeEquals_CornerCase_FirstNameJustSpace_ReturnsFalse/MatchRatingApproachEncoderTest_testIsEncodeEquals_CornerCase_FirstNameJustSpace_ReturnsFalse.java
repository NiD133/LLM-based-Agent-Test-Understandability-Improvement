package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the corner-case behaviour of
 * {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} when the
 * first name consists of nothing but a single space.
 */
public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameJustSpace_ReturnsFalse
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A blank first name carries no phonetic information, so it can never be
     * considered homophonous with a real name. The encoder must therefore
     * report the two inputs as <em>not</em> equal.
     */
    @Test
    final void firstNameThatIsOnlyASpaceIsNeverEqualToAnotherName() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final String blankFirstName = " ";
        final String otherName = "test";

        final boolean namesAreEqual = encoder.isEncodeEquals(blankFirstName, otherName);

        assertFalse(namesAreEqual, "A blank first name must not be treated as equal to another name");
    }
}
