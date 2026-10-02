package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class SoundexTest_testUsMappingEWithAcute extends AbstractStringEncoderTest<Soundex> {

    // U+00E9: LATIN SMALL LETTER E WITH ACUTE (é). Java classifies this as a letter,
    // so the US-English Soundex mapping (which only covers A–Z) cannot handle it and
    // must throw IllegalArgumentException.
    private static final char E_ACUTE = 'é';

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that the default US-English Soundex mapping handles plain ASCII vowels
     * correctly and rejects accented characters that fall outside the A–Z range.
     *
     * <p>Regression test for https://issues.apache.org/jira/browse/CODEC-30 —
     * "fancy" (non-ASCII) characters must not be silently ignored or mis-encoded.</p>
     */
    @Test
    void testUsMappingEWithAcute() {
        // Plain lowercase 'e' is a vowel that anchors the Soundex code as the first letter.
        assertEquals("E000", getStringEncoder().encode("e"));

        if (Character.isLetter(E_ACUTE)) {
            // é is recognised as a letter by the JVM but has no entry in the US mapping,
            // so encoding it must throw IllegalArgumentException (not return garbage output).
            assertThrows(IllegalArgumentException.class,
                    () -> getStringEncoder().encode(String.valueOf(E_ACUTE)));
        } else {
            // If the JVM does not classify é as a letter, SoundexUtils.clean() strips it,
            // leaving an empty string as the encoded result.
            assertEquals("", getStringEncoder().encode(String.valueOf(E_ACUTE)));
        }
    }
}
