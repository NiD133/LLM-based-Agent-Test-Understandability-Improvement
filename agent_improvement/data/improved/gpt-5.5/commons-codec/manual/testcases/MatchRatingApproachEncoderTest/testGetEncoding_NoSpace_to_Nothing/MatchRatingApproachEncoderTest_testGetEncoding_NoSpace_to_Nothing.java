package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_NoSpace_to_Nothing {

    private static final String EMPTY_INPUT = "";
    private static final String EMPTY_ENCODING = "";

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    protected MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testGetEncoding_NoSpace_to_Nothing() {
        assertEquals(EMPTY_ENCODING, getStringEncoder().encode(EMPTY_INPUT));
    }
}
