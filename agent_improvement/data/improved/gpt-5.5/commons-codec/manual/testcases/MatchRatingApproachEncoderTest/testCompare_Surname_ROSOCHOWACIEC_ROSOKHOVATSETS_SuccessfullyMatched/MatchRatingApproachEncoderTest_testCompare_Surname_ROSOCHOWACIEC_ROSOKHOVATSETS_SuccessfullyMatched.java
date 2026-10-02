package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_ROSOCHOWACIEC_ROSOKHOVATSETS_SuccessfullyMatched {

    private static final String ROSOCHOWACIEC_WITH_SPACES = "R o s o ch o w a c ie c";
    private static final String ROSOKHOVATSETS_WITH_SPACES = " R o s o k ho v a ts e ts";

    @Test
    final void testCompare_Surname_ROSOCHOWACIEC_ROSOKHOVATSETS_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertTrue(encoder.isEncodeEquals(ROSOCHOWACIEC_WITH_SPACES, ROSOKHOVATSETS_WITH_SPACES));
    }
}
