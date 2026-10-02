package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testBran {

    private static final String BRAN_ENCODING = "BRAN";

    private final Nysiis strictNysiis = new Nysiis();

    private void assertStrictEncodingForAll(final String expectedEncoding, final String... names) {
        for (final String name : names) {
            assertEquals(expectedEncoding, strictNysiis.encode(name), "Problem with " + name);
        }
    }

    @Test
    void testBran() {
        assertStrictEncodingForAll(BRAN_ENCODING, "Brian", "Brown", "Brun");
    }
}
