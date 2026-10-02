package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_testNewInstance2 extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * Verifies that constructing RefinedSoundex with an explicit char[] mapping
     * (derived from the standard US-English mapping string) produces the same
     * Soundex code as the default instance would for the same input word.
     *
     * "dogs" → 'D' retained as first letter, 'o'→0 (vowel, suppressed),
     * 'g'→4, 's'→3  ⟹  "D643" … wait, the mapping string gives D=6, so
     * the expected code is "D6043".
     */
    @Test
    void testNewInstance2() {
        // Arrange: build an encoder using the char-array constructor with the
        // standard US-English mapping (same data the default constructor uses).
        char[] usEnglishMapping = RefinedSoundex.US_ENGLISH_MAPPING_STRING.toCharArray();
        RefinedSoundex encoder = new RefinedSoundex(usEnglishMapping);
        String inputWord = "dogs";
        String expectedSoundex = "D6043";

        // Act
        String actualSoundex = encoder.soundex(inputWord);

        // Assert: the char-array constructor must behave identically to the
        // default constructor when given the same mapping data.
        assertEquals(expectedSoundex, actualSoundex);
    }
}
