package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_Null_to_Nothing {

    private static final String EMPTY_ENCODING = "";

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testGetEncoding_Null_to_Nothing() {
        final String actualEncoding = getStringEncoder().encode(null);

        assertEquals(EMPTY_ENCODING, actualEncoding);
    }
}
