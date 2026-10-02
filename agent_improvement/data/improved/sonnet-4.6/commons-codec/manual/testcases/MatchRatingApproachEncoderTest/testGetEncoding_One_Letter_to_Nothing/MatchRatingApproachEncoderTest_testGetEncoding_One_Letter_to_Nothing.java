package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_One_Letter_to_Nothing extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The MRA algorithm treats single-character inputs as trivial and returns an empty encoding.
     * This guards the edge case where a name consisting of only one letter (here the vowel "E")
     * should not produce any phonetic code.
     */
    @Test
    final void testGetEncoding_One_Letter_to_Nothing() {
        // A single-letter input is considered trivial by the MRA algorithm and produces no code.
        String singleLetter = "E";
        String expectedEncoding = "";

        String actualEncoding = getStringEncoder().encode(singleLetter);

        assertEquals(expectedEncoding, actualEncoding,
                "MRA encoder should return an empty string for a single-letter input");
    }
}
