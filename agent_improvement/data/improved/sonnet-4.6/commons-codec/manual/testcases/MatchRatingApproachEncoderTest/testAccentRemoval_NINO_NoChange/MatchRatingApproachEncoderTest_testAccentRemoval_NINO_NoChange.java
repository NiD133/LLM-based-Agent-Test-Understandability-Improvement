package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the "NINO" (No Input, No Output) boundary condition of
 * {@link MatchRatingApproachEncoder#removeAccents(String)}: an empty string
 * must be returned unchanged because there are no characters to process.
 */
public class MatchRatingApproachEncoderTest_testAccentRemoval_NINO_NoChange extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that passing an empty string to {@code removeAccents} returns an
     * empty string. The NINO ("No Input, No Output") contract means the encoder
     * must not transform or reject a zero-length input — it simply echoes it back.
     */
    @Test
    final void testAccentRemoval_NINO_NoChange() {
        final String emptyInput = "";
        final String expectedOutput = "";

        final String actualOutput = getStringEncoder().removeAccents(emptyInput);

        assertEquals(expectedOutput, actualOutput,
                "removeAccents(\"\") should return \"\" unchanged (NINO boundary condition)");
    }
}
