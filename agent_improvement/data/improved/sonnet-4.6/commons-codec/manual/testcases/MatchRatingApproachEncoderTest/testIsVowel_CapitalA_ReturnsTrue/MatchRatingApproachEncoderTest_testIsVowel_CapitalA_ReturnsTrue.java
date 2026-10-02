package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsVowel_CapitalA_ReturnsTrue extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsVowel_CapitalA_ReturnsTrue() {
        assertTrue(getStringEncoder().isVowel("A"));
    }
}
