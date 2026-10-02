package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MatchRatingApproachEncoder} returns an empty code for an
 * input that consists only of punctuation characters.
 */
public class MatchRatingApproachEncoderTest_testPunctuationOnly extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * When the input contains nothing but punctuation, the encoder's cleanup
     * step strips every character, leaving no letters to encode. The result
     * must therefore be the empty string.
     */
    @Test
    final void testPunctuationOnly() {
        final String punctuationOnlyInput = ".,-";
        final String expectedEncoding = "";

        assertEquals(expectedEncoding, getStringEncoder().encode(punctuationOnlyInput));
    }
}
