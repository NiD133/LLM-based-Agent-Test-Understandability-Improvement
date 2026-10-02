package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_LongSurnames_OMUIRCHEARTAIGH_OMIREADHAIGH_SuccessfulMatch {

    private static final String OMUIREADHAIGH = "o'muireadhaigh";
    private static final String ACCENTED_OMUIRCHEARTAIGH = "Ó 'Muircheartaigh ";

    @Test
    final void testCompare_LongSurnames_OMUIRCHEARTAIGH_OMIREADHAIGH_SuccessfulMatch() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final boolean surnamesMatch = encoder.isEncodeEquals(OMUIREADHAIGH, ACCENTED_OMUIRCHEARTAIGH);

        assertTrue(surnamesMatch);
    }
}
