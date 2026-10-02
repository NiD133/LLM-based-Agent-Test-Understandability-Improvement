package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_HAILEY_HALLEY_SuccessfullyMatched {

    private static final String SURNAME_HAILEY = "Hailey";
    private static final String SURNAME_HALLEY = "Halley";

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testCompare_Surname_HAILEY_HALLEY_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals(SURNAME_HAILEY, SURNAME_HALLEY));
    }
}
