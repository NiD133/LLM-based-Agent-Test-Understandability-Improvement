package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_SMYTH_to_SMYTH {

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testGetEncoding_SMYTH_to_SMYTH() {
        final String encodedName = getStringEncoder().encode("Smyth");

        assertEquals("SMYTH", encodedName);
    }
}
