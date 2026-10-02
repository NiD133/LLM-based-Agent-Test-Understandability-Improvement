package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testCap {

    private static final String EXPECTED_CAP_ENCODING = "CAP";
    private static final String[] NAMES_ENCODING_TO_CAP = { "Capp", "Cope", "Copp", "Kipp" };

    private final Nysiis stringEncoder = createStringEncoder();

    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    private Nysiis getStringEncoder() {
        return this.stringEncoder;
    }

    private void assertAllEncodeTo(final String[] names, final String expectedEncoding) {
        for (final String name : names) {
            assertEquals(expectedEncoding, getStringEncoder().encode(name), "Problem with " + name);
        }
    }

    @Test
    void testCap() {
        assertAllEncodeTo(NAMES_ENCODING_TO_CAP, EXPECTED_CAP_ENCODING);
    }
}
