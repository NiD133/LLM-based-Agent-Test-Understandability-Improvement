package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Soundex} reproduces the canonical examples published in
 * Microsoft SQL Server's documentation for its {@code SOUNDEX} function.
 *
 * @see <a href=
 *      "https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp">
 *      MS SQL Server SOUNDEX examples</a>
 */
public class SoundexTest_testMsSqlServer1 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * The similar-sounding names "Smith" and "Smythe" must both encode to the
     * same Soundex code "S530", exactly as documented by MS SQL Server.
     */
    @Test
    void testMsSqlServer1() {
        final String expectedSoundexCode = "S530";

        assertEquals(expectedSoundexCode, getStringEncoder().encode("Smith"));
        assertEquals(expectedSoundexCode, getStringEncoder().encode("Smythe"));
    }
}
