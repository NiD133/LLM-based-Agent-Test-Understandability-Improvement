package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetFirstLast3_PETE_Returns_PETE extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * When the input name has 6 or fewer characters, getFirst3Last3 returns it unchanged.
     * "PETE" has 4 characters, so the method returns the full string as-is.
     */
    @Test
    final void testGetFirstLast3_PETE_Returns_PETE() {
        // "PETE" is 4 characters long, which is <= 6, so no truncation occurs
        String shortName = "PETE";
        assertEquals(shortName, getStringEncoder().getFirst3Last3(shortName));
    }
}
