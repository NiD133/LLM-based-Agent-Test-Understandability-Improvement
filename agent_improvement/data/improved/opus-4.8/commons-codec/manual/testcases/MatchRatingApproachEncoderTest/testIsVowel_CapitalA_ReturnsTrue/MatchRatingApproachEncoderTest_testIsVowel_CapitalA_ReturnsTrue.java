package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isVowel(String)}.
 *
 * <p>{@code isVowel} performs a case-insensitive check, so the uppercase
 * letter {@code "A"} must be recognized as a vowel.</p>
 */
public class MatchRatingApproachEncoderTest_testIsVowel_CapitalA_ReturnsTrue
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsVowel_CapitalA_ReturnsTrue() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean isCapitalAVowel = encoder.isVowel("A");

        assertTrue(isCapitalAVowel, "Uppercase \"A\" should be detected as a vowel");
    }
}
