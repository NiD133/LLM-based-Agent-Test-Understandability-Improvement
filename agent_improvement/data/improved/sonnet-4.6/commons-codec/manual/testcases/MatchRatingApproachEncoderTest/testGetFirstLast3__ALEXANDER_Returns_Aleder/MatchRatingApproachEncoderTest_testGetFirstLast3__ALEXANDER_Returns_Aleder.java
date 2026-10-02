package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetFirstLast3__ALEXANDER_Returns_Aleder extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that getFirst3Last3 truncates names longer than 6 characters by
     * returning their first 3 letters concatenated with their last 3 letters.
     *
     * "Alexzander" (10 chars): first 3 = "Ale", last 3 = "der" → "Aleder"
     */
    @Test
    final void testGetFirstLast3__ALEXANDER_Returns_Aleder() {
        // Input has 10 characters, so the algorithm takes first 3 ("Ale") + last 3 ("der")
        String nameWithMoreThanSixChars = "Alexzander";
        String expectedEncoding = "Aleder";

        assertEquals(expectedEncoding, getStringEncoder().getFirst3Last3(nameWithMoreThanSixChars));
    }
}
