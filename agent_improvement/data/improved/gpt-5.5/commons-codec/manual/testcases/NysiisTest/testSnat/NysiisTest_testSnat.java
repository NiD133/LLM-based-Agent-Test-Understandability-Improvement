package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testSnat {

    private final Nysiis encoder = new Nysiis();

    private void encodeAll(final String[] names, final String expectedEncoding) {
        for (final String name : names) {
            assertEquals(expectedEncoding, this.encoder.encode(name), "Problem with " + name);
        }
    }

    @Test
    void testSnat() {
        encodeAll(new String[] { "Smith", "Schmit" }, "SNAT");
    }
}
