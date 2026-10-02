package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_Space_to_Nothing extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A single space is treated as trivial input by the MRA encoder and returns
     * an empty string immediately, without going through the phonetic algorithm.
     */
    @Test
    final void testGetEncoding_Space_to_Nothing() {
        String singleSpace = " ";
        String expected = "";

        String actual = getStringEncoder().encode(singleSpace);

        assertEquals(expected, actual, "Encoding a single space should return an empty string");
    }
}
