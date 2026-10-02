package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testTranan extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that every string in {@code strings} encodes to {@code expectedEncoding}
     * using the strict-mode NYSIIS encoder (max 6-character output).
     */
    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    /**
     * Verifies that phonetically equivalent name spellings "Trueman" and "Truman"
     * both encode to the same NYSIIS key "TRANAN".
     */
    @Test
    void testTranan() {
        encodeAll(new String[] { "Trueman", "Truman" }, "TRANAN");
    }
}
