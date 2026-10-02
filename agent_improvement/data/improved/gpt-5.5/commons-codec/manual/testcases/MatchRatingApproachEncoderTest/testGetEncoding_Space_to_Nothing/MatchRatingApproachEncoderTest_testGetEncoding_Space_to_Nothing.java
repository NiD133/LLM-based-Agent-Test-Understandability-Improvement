package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_Space_to_Nothing {

    private static final String SPACE_ONLY_INPUT = " ";
    private static final String EMPTY_ENCODING = "";

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testGetEncoding_Space_to_Nothing() {
        assertEquals(EMPTY_ENCODING, getStringEncoder().encode(SPACE_ONLY_INPUT));
    }
}
