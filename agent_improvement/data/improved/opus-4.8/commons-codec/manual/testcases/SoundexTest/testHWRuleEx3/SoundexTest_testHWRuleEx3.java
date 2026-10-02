package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies the Soundex "H/W rule": consonants that map to the same Soundex code
 * are collapsed into a single digit even when an 'H' or 'W' sits between them.
 *
 * <p>All inputs in this test therefore encode to the same Soundex code.</p>
 */
public class SoundexTest_testHWRuleEx3 extends AbstractStringEncoderTest<Soundex> {

    /** Every input below is expected to encode to this single Soundex code. */
    private static final String EXPECTED_SOUNDEX_CODE = "S460";

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testHWRuleEx3() throws EncoderException {
        final Soundex soundex = getStringEncoder();

        // "Sgler": the adjacent 'g' and 'l' both map to code 4 and collapse to one digit.
        assertEquals(EXPECTED_SOUNDEX_CODE, soundex.encode("Sgler"));
        // "Swhgler": the same word with a 'w' and an 'h' inserted, which must be ignored.
        assertEquals(EXPECTED_SOUNDEX_CODE, soundex.encode("Swhgler"));

        // A wide range of real surname spellings that all share the same code.
        // @formatter:off
        checkEncodingVariations(EXPECTED_SOUNDEX_CODE,
            "SAILOR", "SALYER", "SAYLOR", "SCHALLER", "SCHELLER", "SCHILLER",
            "SCHOOLER", "SCHULER", "SCHUYLER", "SEILER", "SEYLER", "SHOLAR",
            "SHULER", "SILAR", "SILER", "SILLER");
        // @formatter:on
    }
}
