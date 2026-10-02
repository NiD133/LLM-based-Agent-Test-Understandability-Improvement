package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testHWRuleEx1 {

    private final Soundex stringEncoder = createStringEncoder();

    private Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Consonants from the same Soundex code group are collapsed when separated
     * by H or W, so these examples must not emit duplicate adjacent codes.
     */
    @Test
    void testHWRuleEx1() {
        assertEncodesTo("A261", "Ashcraft");
        assertEncodesTo("A261", "Ashcroft");
        assertEncodesTo("Y330", "yehudit");
        assertEncodesTo("Y330", "yhwdyt");
    }

    private void assertEncodesTo(final String expectedSoundexCode, final String source) {
        assertEquals(expectedSoundexCode, getStringEncoder().encode(source));
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }
}
