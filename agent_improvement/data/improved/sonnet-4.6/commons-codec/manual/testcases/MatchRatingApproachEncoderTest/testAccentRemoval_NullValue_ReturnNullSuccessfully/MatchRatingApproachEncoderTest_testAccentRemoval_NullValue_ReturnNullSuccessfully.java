package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_NullValue_ReturnNullSuccessfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that removeAccents returns null when given a null input,
     * ensuring the method handles null gracefully without throwing an exception.
     */
    @Test
    final void testAccentRemoval_NullValue_ReturnNullSuccessfully() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        String result = encoder.removeAccents(null);
        assertNull(result);
    }
}
