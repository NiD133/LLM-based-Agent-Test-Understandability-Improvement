package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies how the default US English Soundex mapping handles the accented
 * character "o with diaeresis" (ö, Unicode U+00F6).
 *
 * <p>
 * Background: the default US mapping only knows the 26 plain Latin letters
 * A&ndash;Z, so it does not map "fancy" accented characters. See
 * <a href="https://issues.apache.org/jira/browse/CODEC-30">CODEC-30</a>.
 * </p>
 */
public class SoundexTest_testUsMappingOWithDiaeresis extends AbstractStringEncoderTest<Soundex> {

    /** The accented character under test: lowercase "o with diaeresis" (ö). */
    private static final String O_WITH_DIAERESIS = "ö";

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testUsMappingOWithDiaeresis() {
        final Soundex soundex = getStringEncoder();

        // A plain "o" is a recognised letter and encodes to the standard 4-character code.
        assertEquals("O000", soundex.encode("o"));

        // The accented "ö" is outside the default A-Z mapping. Its handling depends on
        // whether the current JVM/locale considers it a letter:
        if (Character.isLetter('ö')) {
            // Treated as a letter, but it has no mapping, so encoding must fail.
            assertThrows(IllegalArgumentException.class, () -> soundex.encode(O_WITH_DIAERESIS));
        } else {
            // Treated as a non-letter, so it is stripped out and the result is empty.
            assertEquals("", soundex.encode(O_WITH_DIAERESIS));
        }
    }
}
