package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests Soundex encoding for a batch of representative surnames drawn from
 * http://www.archives.gov/research_room/genealogy/census/soundex.html.
 *
 * <p>Soundex rules under test:
 * <ul>
 *   <li>The first letter is kept as-is.</li>
 *   <li>H and W are ignored (they are not encoded and do not separate adjacent
 *       consonants with the same code).</li>
 *   <li>Consecutive consonants that map to the same digit are collapsed to one.</li>
 *   <li>The code is always padded to exactly 4 characters with trailing zeros.</li>
 * </ul>
 */
public class SoundexTest_testEncodeBatch3 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that a surname produces the expected four-character Soundex code.
     *
     * <p>The "VanDeusen" row has an alternative interpretation (D250) where the
     * leading "Van" prefix is treated as a separate word, but the standard
     * Soundex algorithm encodes the full name as a single string, yielding V532.
     */
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
        "Washington, W252",
        "Lee,        L000",
        "Gutierrez,  G362",
        "Pfister,    P236",
        "Jackson,    J250",
        "Tymczak,    T522",
        // D-250 (D, 2 for S, 5 for N, 0 padded) is an alternative reading for VanDeusen.
        "VanDeusen,  V532"
    })
    void testEncodeBatch3(final String name, final String expectedSoundex) {
        assertEquals(expectedSoundex.trim(), getStringEncoder().encode(name));
    }
}
