package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testRule7 {

    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Takes input/expected encoding pairs and checks them against the full NYSIIS encoder.
     *
     * @param encodingPairs
     *            each pair contains the input string followed by its expected encoding.
     */
    private void assertEncodings(final String[]... encodingPairs) {
        for (final String[] encodingPair : encodingPairs) {
            final String input = encodingPair[0];
            final String expectedEncoding = encodingPair[1];
            assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
        }
    }

    /**
     * Tests rule 7: If last character is A, remove it.
     */
    @Test
    void testRule7() {
        assertEncodings(
                new String[] { "XA", "X" }, // Rules 5, 7
                new String[] { "XAS", "X" });
    }
}
