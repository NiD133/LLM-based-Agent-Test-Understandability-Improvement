package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveVowel_ALESSANDRA_Returns_ALSSNDR extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testRemoveVowel_ALESSANDRA_Returns_ALSSNDR() {
        // All vowels are stripped, then the leading vowel 'A' is re-prepended: ALESSANDRA → LSSNDR → ALSSNDR
        assertEquals("ALSSNDR", getStringEncoder().removeVowels("ALESSANDRA"));
    }
}
