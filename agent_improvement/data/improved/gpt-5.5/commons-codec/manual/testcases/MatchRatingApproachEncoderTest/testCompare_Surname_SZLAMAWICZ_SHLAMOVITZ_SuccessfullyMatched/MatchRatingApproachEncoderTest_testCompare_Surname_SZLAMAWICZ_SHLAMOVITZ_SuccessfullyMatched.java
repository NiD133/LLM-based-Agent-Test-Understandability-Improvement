package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_SZLAMAWICZ_SHLAMOVITZ_SuccessfullyMatched {

    private static final String ORIGINAL_SURNAME = "SZLAMAWICZ";
    private static final String PHONETICALLY_SIMILAR_SURNAME = "SHLAMOVITZ";

    @Test
    final void testCompare_Surname_SZLAMAWICZ_SHLAMOVITZ_SuccessfullyMatched() {
        final boolean surnamesMatch = new MatchRatingApproachEncoder().isEncodeEquals(
                ORIGINAL_SURNAME,
                PHONETICALLY_SIMILAR_SURNAME);

        assertTrue(surnamesMatch);
    }
}
