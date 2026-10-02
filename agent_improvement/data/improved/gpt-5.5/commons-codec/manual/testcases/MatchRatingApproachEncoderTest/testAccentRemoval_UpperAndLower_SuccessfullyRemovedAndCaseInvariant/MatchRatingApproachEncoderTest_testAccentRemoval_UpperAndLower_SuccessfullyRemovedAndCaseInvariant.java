package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_UpperAndLower_SuccessfullyRemovedAndCaseInvariant {

    private static final String ACCENTED_MIXED_CASE_NAME = "ÁeíÓuu";
    private static final String UNACCENTED_MIXED_CASE_NAME = "AeiOuu";

    @Test
    final void testAccentRemoval_UpperAndLower_SuccessfullyRemovedAndCaseInvariant() {
        assertEquals(UNACCENTED_MIXED_CASE_NAME, new MatchRatingApproachEncoder().removeAccents(ACCENTED_MIXED_CASE_NAME));
    }
}
