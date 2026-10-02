package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testMsSqlServer2 {

    private static final String MS_SQL_SERVER_ERICKSON_CODE = "E625";

    /**
     * Examples for MS SQL Server from:
     * https://support.microsoft.com/default.aspx?scid=https://support.microsoft.com:80/support
     * /kb/articles/Q100/3/65.asp&NoWebContent=1
     *
     */
    @Test
    void testMsSqlServer2() {
        final Soundex soundex = new Soundex();

        assertEquals(MS_SQL_SERVER_ERICKSON_CODE, soundex.soundex("Erickson"));
        assertEquals(MS_SQL_SERVER_ERICKSON_CODE, soundex.soundex("Erickson"));
        assertEquals(MS_SQL_SERVER_ERICKSON_CODE, soundex.soundex("Erikson"));
        assertEquals(MS_SQL_SERVER_ERICKSON_CODE, soundex.soundex("Ericson"));
        assertEquals(MS_SQL_SERVER_ERICKSON_CODE, soundex.soundex("Ericksen"));
        assertEquals(MS_SQL_SERVER_ERICKSON_CODE, soundex.soundex("Ericsen"));
    }
}
