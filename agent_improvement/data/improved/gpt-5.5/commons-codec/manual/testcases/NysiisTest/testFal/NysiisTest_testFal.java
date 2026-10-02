package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testFal {

    private Nysiis createStringEncoder() {
        return new Nysiis();
    }

    private Nysiis getStringEncoder() {
        return createStringEncoder();
    }

    private void encodeAll(final String[] inputs, final String expectedEncoding) {
        for (final String input : inputs) {
            assertEquals(expectedEncoding, getStringEncoder().encode(input), "Problem with " + input);
        }
    }

    @Test
    void testFal() {
        encodeAll(new String[] { "Phil" }, "FAL");
    }
}
