package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testMsSqlServer1 {

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return createStringEncoder();
    }

    /**
     * Examples for MS SQLServer from
     * https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
     */
    @Test
    void testMsSqlServer1() {
        final String expectedSqlServerSoundexCode = "S530";

        assertEquals(expectedSqlServerSoundexCode, getStringEncoder().encode("Smith"));
        assertEquals(expectedSqlServerSoundexCode, getStringEncoder().encode("Smythe"));
    }
}
