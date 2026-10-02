package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_PETERSON_PETERS_SuccessfullyMatched {

    private final MatchRatingApproachEncoder stringEncoder = new MatchRatingApproachEncoder();

    @Test
    final void testCompare_PETERSON_PETERS_SuccessfullyMatched() {
        final String fullSurname = "Peterson";
        final String shortenedSurname = "Peters";

        final boolean surnamesMatch = stringEncoder.isEncodeEquals(fullSurname, shortenedSurname);

        assertTrue(surnamesMatch);
    }
}
