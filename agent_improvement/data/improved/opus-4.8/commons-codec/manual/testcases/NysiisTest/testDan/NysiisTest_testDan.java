package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the NYSIIS encoder maps a group of phonetically similar names to the same code.
 */
public class NysiisTest_testDan extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that the given name encodes to the supplied expected NYSIIS code.
     *
     * @param name         the name to encode.
     * @param expectedCode the expected NYSIIS encoding.
     */
    private void assertEncodesTo(final String name, final String expectedCode) {
        assertEquals(expectedCode, getStringEncoder().encode(name), "Problem with " + name);
    }

    @Test
    void testDan() {
        // All three names share the "DAN" phonetic encoding.
        assertEncodesTo("Dane", "DAN");
        assertEncodesTo("Dean", "DAN");
        assertEncodesTo("Dionne", "DAN");
    }
}
