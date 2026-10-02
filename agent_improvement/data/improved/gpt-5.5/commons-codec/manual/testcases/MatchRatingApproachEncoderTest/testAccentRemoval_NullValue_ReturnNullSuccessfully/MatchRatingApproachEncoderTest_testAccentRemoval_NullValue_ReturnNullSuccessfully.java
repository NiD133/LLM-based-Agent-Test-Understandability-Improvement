package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_NullValue_ReturnNullSuccessfully {

    @Test
    final void testAccentRemoval_NullValue_ReturnNullSuccessfully() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertNull(encoder.removeAccents(null));
    }
}
