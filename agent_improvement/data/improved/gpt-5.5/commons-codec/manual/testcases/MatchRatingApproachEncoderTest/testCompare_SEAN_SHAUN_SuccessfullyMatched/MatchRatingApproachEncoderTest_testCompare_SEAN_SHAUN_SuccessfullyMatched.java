package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SEAN_SHAUN_SuccessfullyMatched {

    @Test
    final void accentedSeanAndShaunAreMatchedByMatchRatingApproach() {
        assertTrue(new MatchRatingApproachEncoder().isEncodeEquals("Séan", "Shaun"));
    }
}
