package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testHWRuleEx3 extends AbstractStringEncoderTest<Soundex> {

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
        assertEquals("S460", getStringEncoder().encode("Sgler"));
        assertEquals("S460", getStringEncoder().encode("Swhgler"));
        // Also S460:
        // @formatter:off
        checkEncodingVariations("S460", "SAILOR", "SALYER", "SAYLOR", "SCHALLER", "SCHELLER", "SCHILLER", "SCHOOLER", "SCHULER", "SCHUYLER", "SEILER", "SEYLER", "SHOLAR", "SHULER", "SILAR", "SILER", "SILLER");
        // @formatter:on
    }
}
