package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies Soundex's "H/W rule": consonants belonging to the same code group that are
 * separated only by an H or a W are collapsed into a single code digit.
 *
 * <p>Both spellings of "Booth Davis" below encode to {@code "B312"} because the silent
 * H (in "BOOTH") is ignored and the separator hyphen is stripped before encoding.</p>
 *
 * <p>Test data from http://www.myatt.demon.co.uk/sxalg.htm</p>
 */
public class SoundexTest_testHWRuleEx2 extends AbstractStringEncoderTest<Soundex> {

    /** The expected Soundex code shared by every spelling of "Booth Davis". */
    private static final String EXPECTED_SOUNDEX_CODE = "B312";

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testHWRuleEx2() {
        // The same name written with and without a hyphen must yield the same code.
        assertEquals(EXPECTED_SOUNDEX_CODE, getStringEncoder().encode("BOOTHDAVIS"),
                "Plain spelling should encode to the shared code");
        assertEquals(EXPECTED_SOUNDEX_CODE, getStringEncoder().encode("BOOTH-DAVIS"),
                "Hyphenated spelling should encode to the same code");
    }
}
