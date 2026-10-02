package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_LongSurnames_MORIARTY_OMUIRCHEARTAIGH_DoesNotSuccessfulMatch
{

    private static final String SURNAME_MORIARTY = "Moriarty";
    private static final String SURNAME_OMUIRCHEARTAIGH = "OMuircheartaigh";

    private final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

    @Test
    final void longSurnamesWithDifferentMatchRatingCodesDoNotMatch() {
        final boolean encodedNamesMatch = encoder.isEncodeEquals(
                SURNAME_MORIARTY,
                SURNAME_OMUIRCHEARTAIGH);

        assertFalse(encodedNamesMatch);
    }
}
