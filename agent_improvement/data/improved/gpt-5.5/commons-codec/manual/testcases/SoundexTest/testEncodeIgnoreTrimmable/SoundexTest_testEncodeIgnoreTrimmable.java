package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testEncodeIgnoreTrimmable {

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    void testEncodeIgnoreTrimmable() {
        final String trimmableWashington = " \t\n\r Washington \t\n\r ";

        assertEquals("W252", getStringEncoder().encode(trimmableWashington));
    }
}
