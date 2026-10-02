package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testDan {

    private static final String DAN_ENCODING = "DAN";
    private static final String[] DAN_VARIANTS = { "Dane", "Dean", "Dionne" };
    private final Nysiis stringEncoder = new Nysiis();

    private void assertAllEncodeTo(final String[] inputs, final String expectedEncoding) {
        for (final String input : inputs) {
            assertEquals(expectedEncoding, stringEncoder.encode(input), "Problem with " + input);
        }
    }

    @Test
    void testDan() {
        assertAllEncodeTo(DAN_VARIANTS, DAN_ENCODING);
    }
}
