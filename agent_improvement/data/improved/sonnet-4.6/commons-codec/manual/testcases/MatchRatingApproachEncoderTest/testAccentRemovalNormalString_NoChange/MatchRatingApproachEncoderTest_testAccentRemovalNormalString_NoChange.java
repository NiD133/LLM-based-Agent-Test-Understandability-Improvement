package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@code removeAccents} leaves a plain ASCII string unchanged,
 * because there are no accented characters to replace.
 */
public class MatchRatingApproachEncoderTest_testAccentRemovalNormalString_NoChange extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testAccentRemovalNormalString_NoChange() {
        // A sentence containing only plain ASCII letters — no accented characters present.
        // removeAccents should return the string exactly as supplied.
        String plainAsciiSentence = "Colorless green ideas sleep furiously";

        assertEquals(plainAsciiSentence, getStringEncoder().removeAccents(plainAsciiSentence));
    }
}
