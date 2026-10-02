package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_One_Letter_to_Nothing {

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testGetEncoding_One_Letter_to_Nothing() {
        final String oneLetterName = "E";
        final String noEncoding = "";

        assertEquals(noEncoding, getStringEncoder().encode(oneLetterName));
    }
}
