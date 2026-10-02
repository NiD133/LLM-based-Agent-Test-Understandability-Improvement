package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_NoSpace_to_Nothing extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetEncoding_NoSpace_to_Nothing() {
        // The MRA encoder treats an empty string as trivial input and returns an empty string
        final String emptyInput = "";
        final String expectedOutput = "";
        assertEquals(expectedOutput, getStringEncoder().encode(emptyInput),
                "Encoding an empty string should return an empty string");
    }
}
