package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testHWRuleEx3 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies the H/W rule: after the first character, H and W are completely
     * silent and do not act as separators between consonants. This means that
     * inserting H or W between two letters does not change the resulting Soundex
     * code, regardless of whether those letters share the same code group.
     *
     * <p>Example pair: "Sgler" and "Swhgler" both encode to "S460" because the
     * inserted 'wh' is silently skipped.</p>
     *
     * <p>The rule also explains why many differently-spelled surnames (SAILOR,
     * SCHILLER, SHULER, etc.) all collapse to the same Soundex code S460.</p>
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testHWRuleEx3() throws EncoderException {
        final String expectedCode = "S460";

        // Baseline encoding without any H or W between consonants
        assertEquals(expectedCode, getStringEncoder().encode("Sgler"));

        // Inserting 'wh' between the same consonants still yields S460,
        // proving that H and W are silently ignored after the first character
        assertEquals(expectedCode, getStringEncoder().encode("Swhgler"));

        // Real-world surname spellings that all map to S460 via the H/W rule
        // and other Soundex collapsing rules (vowels, duplicate codes, etc.)
        checkEncodingVariations(expectedCode,
                "SAILOR", "SALYER", "SAYLOR",
                "SCHALLER", "SCHELLER", "SCHILLER", "SCHOOLER", "SCHULER", "SCHUYLER",
                "SEILER", "SEYLER",
                "SHOLAR", "SHULER",
                "SILAR", "SILER", "SILLER");
    }
}
