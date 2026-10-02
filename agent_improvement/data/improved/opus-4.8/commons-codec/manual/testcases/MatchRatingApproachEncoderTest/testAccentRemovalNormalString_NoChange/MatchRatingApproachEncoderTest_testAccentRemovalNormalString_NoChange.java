package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#removeAccents(String)}.
 */
public class MatchRatingApproachEncoderTest_testAccentRemovalNormalString_NoChange
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A string that contains no accented characters should be returned unchanged
     * by {@link MatchRatingApproachEncoder#removeAccents(String)}.
     */
    @Test
    final void testAccentRemovalNormalString_NoChange() {
        final String accentFreeText = "Colorless green ideas sleep furiously";

        final String result = getStringEncoder().removeAccents(accentFreeText);

        assertEquals(accentFreeText, result,
                "A string without accents should be left unchanged");
    }
}
