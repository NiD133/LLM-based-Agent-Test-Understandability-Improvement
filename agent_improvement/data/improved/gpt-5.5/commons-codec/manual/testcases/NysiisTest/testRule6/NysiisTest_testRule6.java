package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testRule6 {

    private static final int INPUT = 0;
    private static final int EXPECTED_ENCODING = 1;

    private final Nysiis fullNysiis = new Nysiis(false);

    private void assertFullEncodings(final String[]... testValues) {
        for (final String[] testValue : testValues) {
            final String input = testValue[INPUT];
            final String expectedEncoding = testValue[EXPECTED_ENCODING];

            assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
        }
    }

    /**
     * Tests rule 6: If last characters are AY, replace with Y.
     */
    @Test
    void testRule6() {
        assertFullEncodings(
                new String[] { "XAY", "XY" }, // Rules 5, 6
                new String[] { "XAYS", "XY" });
    }
}
