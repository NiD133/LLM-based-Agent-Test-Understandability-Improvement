package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_NINO_NoChange {

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void removeAccentsReturnsEmptyStringForEmptyInput() {
        assertEquals("", createStringEncoder().removeAccents(""));
    }
}
