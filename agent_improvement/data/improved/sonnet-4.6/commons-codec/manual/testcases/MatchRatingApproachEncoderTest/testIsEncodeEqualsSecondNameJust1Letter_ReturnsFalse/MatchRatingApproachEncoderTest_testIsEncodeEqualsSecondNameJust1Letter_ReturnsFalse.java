package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEqualsSecondNameJust1Letter_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The MRA algorithm rejects any comparison where either name is a single character,
     * so isEncodeEquals must return false when the second name has length 1.
     */
    @Test
    final void testIsEncodeEqualsSecondNameJust1Letter_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("test", "t"));
    }
}
