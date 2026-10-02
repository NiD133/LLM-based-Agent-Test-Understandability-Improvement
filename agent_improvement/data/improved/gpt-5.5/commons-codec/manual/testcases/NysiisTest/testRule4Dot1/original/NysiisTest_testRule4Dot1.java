package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testRule4Dot1 extends AbstractStringEncoderTest<Nysiis> {

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
     * Tests rule 4.1: EV → AF else A, E, I, O, U → A
     */
    @Test
    void testRule4Dot1() {
        assertEncodings(new String[] { "XEV", "XAF" }, new String[] { "XAX", "XAX" }, new String[] { "XEX", "XAX" }, new String[] { "XIX", "XAX" }, new String[] { "XOX", "XAX" }, new String[] { "XUX", "XAX" });
    }
}
