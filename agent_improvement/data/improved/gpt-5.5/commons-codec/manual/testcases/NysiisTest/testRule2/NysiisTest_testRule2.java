package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testRule2 {

    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Takes an array of String pairs where each pair's first element is the input and the second element the expected
     * encoding.
     *
     * @param testValues
     *            an array of String pairs where each pair's first element is the input and the second element the
     *            expected encoding.
     */
    private void assertEncodings(final String[]... testValues) {
        for (final String[] encodingCase : testValues) {
            final String input = encodingCase[0];
            final String expectedEncoding = encodingCase[1];
            assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
        }
    }

    /**
     * Tests rule 2: Translate last characters of name: EE -> Y, IE -> Y, DT, RT, RD, NT, ND -> D
     */
    @Test
    void testRule2() {
        assertEncodings(
                new String[] { "XEE", "XY" },
                new String[] { "XIE", "XY" },
                new String[] { "XDT", "XD" },
                new String[] { "XRT", "XD" },
                new String[] { "XRD", "XD" },
                new String[] { "XNT", "XD" },
                new String[] { "XND", "XD" });
    }
}
