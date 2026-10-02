package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testRule2 extends AbstractStringEncoderTest<Nysiis> {

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
        for (final String[] arr : testValues) {
            assertEquals(arr[1], this.fullNysiis.encode(arr[0]), "Problem with " + arr[0]);
        }
    }

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    /**
     * Tests rule 2: Translate last characters of name: EE → Y, IE → Y, DT, RT, RD, NT, ND → D
     */
    @Test
    void testRule2() {
        assertEncodings(
            // Rule 2a: trailing EE and IE both map to Y
            new String[] { "XEE", "XY" },
            new String[] { "XIE", "XY" },
            // Rule 2b: trailing DT, RT, RD, NT, ND all map to D
            new String[] { "XDT", "XD" },
            new String[] { "XRT", "XD" },
            new String[] { "XRD", "XD" },
            new String[] { "XNT", "XD" },
            new String[] { "XND", "XD" }
        );
    }
}
