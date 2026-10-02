package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testTranan {

    private final Nysiis nysiis = new Nysiis();

    private void encodeAll(final String[] names, final String expectedEncoding) {
        for (final String name : names) {
            assertEquals(expectedEncoding, this.nysiis.encode(name), "Problem with " + name);
        }
    }

    @Test
    void testTranan() {
        final String[] namesWithSameEncoding = { "Trueman", "Truman" };
        final String expectedEncoding = "TRANAN";

        encodeAll(namesWithSameEncoding, expectedEncoding);
    }
}
