package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testOthers {

    private static final int INPUT = 0;
    private static final int EXPECTED_ENCODING = 1;

    private final Nysiis fullNysiis = new Nysiis(false);

    private void assertFullNysiisEncodings(final String[]... encodingPairs) {
        for (final String[] encodingPair : encodingPairs) {
            final String input = encodingPair[INPUT];
            final String expectedEncoding = encodingPair[EXPECTED_ENCODING];

            assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
        }
    }

    /**
     * Tests data gathered from around the internets.
     */
    @Test
    void testOthers() {
        assertFullNysiisEncodings(
                new String[] { "O'Daniel", "ODANAL" },
                new String[] { "O'Donnel", "ODANAL" },
                new String[] { "Cory", "CARY" },
                new String[] { "Corey", "CARY" },
                new String[] { "Kory", "CARY" },
                new String[] { "FUZZY", "FASY" });
    }
}
