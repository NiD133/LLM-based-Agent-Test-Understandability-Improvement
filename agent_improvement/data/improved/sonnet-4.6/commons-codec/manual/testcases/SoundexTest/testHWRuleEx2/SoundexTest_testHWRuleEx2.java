package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class SoundexTest_testHWRuleEx2 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies the H/W-rule: H and W between consonants that share the same Soundex
     * code group are treated as silent, so the second consonant is suppressed as a
     * duplicate rather than acting as a new code.
     *
     * Encoding breakdown for "BOOTHDAVIS":
     *   B -> retained as first letter
     *   OO -> vowels (code 0), skipped
     *   T  -> code 3, added  (count = 2)
     *   H  -> silent (special-case H/W rule), skipped entirely
     *   D  -> code 3, same as previous digit T, suppressed as duplicate
     *   A  -> vowel, skipped
     *   V  -> code 1, added  (count = 3)
     *   I  -> vowel, skipped
     *   S  -> code 2, added  (count = 4)
     *   Result: B312
     *
     * "BOOTH-DAVIS" is normalised (hyphen stripped) to the same letter sequence before
     * encoding, so it produces an identical Soundex code.
     *
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm
     */
    @Test
    void testHWRuleEx2() {
        final String expectedSoundexCode = "B312";

        assertEquals(expectedSoundexCode, getStringEncoder().encode("BOOTHDAVIS"),
                "H between T and D (same code group) should be silent, causing D to be suppressed as a duplicate");

        assertEquals(expectedSoundexCode, getStringEncoder().encode("BOOTH-DAVIS"),
                "Hyphenated form should produce the same code after the hyphen is stripped during normalisation");
    }
}
