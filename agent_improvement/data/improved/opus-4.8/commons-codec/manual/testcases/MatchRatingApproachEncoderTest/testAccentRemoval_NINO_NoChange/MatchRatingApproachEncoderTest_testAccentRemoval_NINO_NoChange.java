package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the "Nothing In, Nothing Out" (NINO) behavior of
 * {@link MatchRatingApproachEncoder#removeAccents(String)}: an empty input has
 * no accented characters to strip, so the result must be the same empty string.
 */
public class MatchRatingApproachEncoderTest_testAccentRemoval_NINO_NoChange
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void removeAccentsOnEmptyStringReturnsEmptyString() {
        final String emptyInput = "";

        final String result = getStringEncoder().removeAccents(emptyInput);

        assertEquals("", result, "Removing accents from an empty string should leave it unchanged");
    }
}
