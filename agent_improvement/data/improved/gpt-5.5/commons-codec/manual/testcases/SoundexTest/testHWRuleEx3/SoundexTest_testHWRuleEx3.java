package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testHWRuleEx3 {

    private static final String EXPECTED_SOUNDEX = "S460";

    private final Soundex stringEncoder = createStringEncoder();

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }

    private void checkEncodingVariations(final String expected, final String... variations) {
        for (final String variation : variations) {
            assertEquals(expected, getStringEncoder().encode(variation));
        }
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one
     * sound, so inserting W and H into "Sgler" does not change the Soundex code.
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testHWRuleEx3() throws EncoderException {
        assertEquals(EXPECTED_SOUNDEX, getStringEncoder().encode("Sgler"));
        assertEquals(EXPECTED_SOUNDEX, getStringEncoder().encode("Swhgler"));

        checkEncodingVariations(
                EXPECTED_SOUNDEX,
                "SAILOR",
                "SALYER",
                "SAYLOR",
                "SCHALLER",
                "SCHELLER",
                "SCHILLER",
                "SCHOOLER",
                "SCHULER",
                "SCHUYLER",
                "SEILER",
                "SEYLER",
                "SHOLAR",
                "SHULER",
                "SILAR",
                "SILER",
                "SILLER");
    }
}
