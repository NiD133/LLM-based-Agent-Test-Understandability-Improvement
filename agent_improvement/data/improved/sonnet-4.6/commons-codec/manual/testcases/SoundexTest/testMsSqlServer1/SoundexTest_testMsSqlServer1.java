package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class SoundexTest_testMsSqlServer1 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies Soundex encoding matches MS SQL Server examples.
     *
     * "Smith" and "Smythe" are phonetically equivalent names and should
     * produce the same Soundex code ("S530"). The 'y' in "Smythe" acts
     * as a vowel and is not encoded, while 'th' collapses to '3', same
     * as 't' in "Smith".
     *
     * Reference: MS SQL Server SOUNDEX documentation
     * https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
     */
    @Test
    void testMsSqlServer1() {
        Soundex soundex = getStringEncoder();
        String expectedCode = "S530";

        assertEquals(expectedCode, soundex.encode("Smith"),
                "\"Smith\" should encode to S530 per MS SQL Server Soundex rules");
        assertEquals(expectedCode, soundex.encode("Smythe"),
                "\"Smythe\" should encode to S530 — phonetically equivalent to \"Smith\"");
    }
}
