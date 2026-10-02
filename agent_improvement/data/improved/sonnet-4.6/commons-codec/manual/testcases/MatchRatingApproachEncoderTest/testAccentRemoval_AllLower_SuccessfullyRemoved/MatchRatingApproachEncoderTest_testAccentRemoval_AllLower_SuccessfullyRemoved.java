package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_AllLower_SuccessfullyRemoved extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that lowercase accented vowels (á é í ó ú) are each replaced
     * with their plain ASCII equivalents (a e i o u), preserving case.
     */
    @Test
    final void testAccentRemoval_AllLower_SuccessfullyRemoved() {
        assertEquals("aeiou", getStringEncoder().removeAccents("áéíóú"));
    }
}
