package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testOthers extends AbstractStringEncoderTest<Nysiis> {

    // Full (non-strict) NYSIIS encoder: no 6-character length cap on the output.
    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Asserts that each input string encodes to its paired expected value under the full NYSIIS encoder.
     * Each element of {@code testValues} is a two-element array: [input, expectedEncoding].
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
     * Tests data gathered from around the internets.
     *
     * Verifies phonetically equivalent name variants encode to the same NYSIIS key,
     * and that a simple consonant-cluster transformation ("FUZZY") is handled correctly.
     */
    @Test
    void testOthers() {
        assertEncodings(
            // "O'Daniel" and "O'Donnel" are phonetically similar Irish-origin names;
            // both should collapse to the same NYSIIS key "ODANAL".
            new String[] { "O'Daniel", "ODANAL" },
            new String[] { "O'Donnel", "ODANAL" },

            // "Cory", "Corey", and "Kory" are spelling/pronunciation variants of the same name;
            // the leading-K -> C rule and vowel normalisation should all yield "CARY".
            new String[] { "Cory",  "CARY" },
            new String[] { "Corey", "CARY" },
            new String[] { "Kory",  "CARY" },

            // "FUZZY" exercises the Z -> S and vowel-normalisation rules, producing "FASY".
            new String[] { "FUZZY", "FASY" }
        );
    }
}
