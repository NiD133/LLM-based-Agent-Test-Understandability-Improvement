package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testRule4Dot1 {

    private final Nysiis fullNysiis = new Nysiis(false);

    private void assertEncodings(final String[]... testValues) {
        for (final String[] testValue : testValues) {
            final String input = testValue[0];
            final String expectedEncoding = testValue[1];

            assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
        }
    }

    /**
     * Tests rule 4.1: EV -> AF, otherwise A, E, I, O, U -> A.
     */
    @Test
    void testRule4Dot1() {
        assertEncodings(
                new String[] { "XEV", "XAF" },
                new String[] { "XAX", "XAX" },
                new String[] { "XEX", "XAX" },
                new String[] { "XIX", "XAX" },
                new String[] { "XOX", "XAX" },
                new String[] { "XUX", "XAX" });
    }
}
