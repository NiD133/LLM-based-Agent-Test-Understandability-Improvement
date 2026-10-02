package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testDifference extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * The difference() contract for null and empty/whitespace-only inputs:
     * RefinedSoundex encodes both to an empty/null code, so the overlap is 0.
     */
    @Test
    void testDifference_nullAndBlankInputsReturnZero() throws EncoderException {
        assertEquals(0, getStringEncoder().difference(null, null));
        assertEquals(0, getStringEncoder().difference("", ""));
        assertEquals(0, getStringEncoder().difference(" ", " "));
    }

    /**
     * Pairs of names that sound similar to each other.
     * Higher scores (closer to the length of the shorter encoded form) indicate
     * stronger phonetic similarity.
     */
    @Test
    void testDifference_phoneticallySimlarNames() throws EncoderException {
        // "Smith" and "Smythe" share nearly every encoded character.
        assertEquals(6, getStringEncoder().difference("Smith", "Smythe"));

        // "Ann" and "Andrew" share their opening encoded character.
        assertEquals(3, getStringEncoder().difference("Ann", "Andrew"));
    }

    /**
     * Pairs of names that sound dissimilar to each other.
     * A score of 1 means only the first encoded character matches.
     */
    @Test
    void testDifference_phoneticallyDissimilarNames() throws EncoderException {
        assertEquals(1, getStringEncoder().difference("Margaret", "Andrew"));
        assertEquals(1, getStringEncoder().difference("Janet", "Margaret"));
    }

    /**
     * Examples from the Microsoft T-SQL DIFFERENCE documentation
     * (https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_de-dz_8co5.asp):
     * "Green"/"Greene" differ only by a silent trailing 'e', so their codes are nearly identical.
     * "Blotchet-Halls" and "Greene" share almost no phonetic structure.
     */
    @Test
    void testDifference_msdnExamplesGreenBlotchet() throws EncoderException {
        assertEquals(5, getStringEncoder().difference("Green", "Greene"));
        assertEquals(1, getStringEncoder().difference("Blotchet-Halls", "Greene"));
    }

    /**
     * Examples from the Microsoft T-SQL SOUNDEX documentation
     * (https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp):
     * longer words with the same phonetic root produce higher overlap scores.
     */
    @Test
    void testDifference_msdnExamplesSmithAnothers() throws EncoderException {
        assertEquals(6, getStringEncoder().difference("Smith", "Smythe"));
        assertEquals(8, getStringEncoder().difference("Smithers", "Smythers"));
        assertEquals(5, getStringEncoder().difference("Anothers", "Brothers"));
    }
}
