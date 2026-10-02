package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getFirst3Last3(String)} for a short name.
 *
 * <p>The method keeps only the first and last three letters of a name, but only when the
 * name is longer than six characters. For shorter inputs it returns the name unchanged.
 * "PETE" has just four characters, so it should come back exactly as supplied.</p>
 */
public class MatchRatingApproachEncoderTest_testGetFirstLast3_PETE_Returns_PETE
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetFirstLast3_PETE_Returns_PETE() {
        // "PETE" is 4 characters (<= 6), so getFirst3Last3 returns it unchanged.
        final String shortName = "PETE";

        final String result = getStringEncoder().getFirst3Last3(shortName);

        assertEquals(shortName, result, "A name of six or fewer characters is returned unchanged");
    }
}
