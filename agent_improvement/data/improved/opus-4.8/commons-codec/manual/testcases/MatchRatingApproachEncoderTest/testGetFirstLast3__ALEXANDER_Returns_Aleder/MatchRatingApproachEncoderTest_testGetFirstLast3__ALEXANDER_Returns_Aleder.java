package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getFirst3Last3(String)} for a name
 * longer than 6 characters.
 *
 * <p>For such names the method keeps only the first three and the last three
 * letters, concatenated together. Given {@code "Alexzander"} (10 letters) the
 * first three letters are {@code "Ale"} and the last three are {@code "der"},
 * so the expected result is {@code "Aleder"}.</p>
 */
public class MatchRatingApproachEncoderTest_testGetFirstLast3__ALEXANDER_Returns_Aleder
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getFirst3Last3_keepsFirstAndLastThreeLetters_forLongName() {
        final String longName = "Alexzander";
        final String expectedFirst3Last3 = "Aleder";

        final String actual = getStringEncoder().getFirst3Last3(longName);

        assertEquals(expectedFirst3Last3, actual);
    }
}
