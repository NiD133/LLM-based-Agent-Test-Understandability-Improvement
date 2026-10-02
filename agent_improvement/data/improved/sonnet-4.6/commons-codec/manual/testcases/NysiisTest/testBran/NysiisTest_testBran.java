package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that the NYSIIS encoder produces the same phonetic key "BRAN" for names
 * that sound like "Brian"/"Brown"/"Brun" — verifying the algorithm's ability to
 * group phonetically similar names under a single code.
 */
public class NysiisTest_testBran extends AbstractStringEncoderTest<Nysiis> {

    /**
     * Non-strict (unbounded-length) NYSIIS encoder used by {@link #assertEncodings}.
     * The default strict encoder (max 6 chars) is provided by {@link #getStringEncoder()}.
     */
    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Asserts that each input/expected pair in {@code testValues} encodes correctly
     * using the non-strict {@link #fullNysiis} encoder.
     *
     * @param testValues pairs where {@code [0]} is the input name and {@code [1]} is
     *                   the expected NYSIIS code.
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

    /**
     * Asserts that every name in {@code strings} encodes to {@code expectedEncoding}
     * using the strict (default) NYSIIS encoder returned by {@link #getStringEncoder()}.
     *
     * @param strings          the input names to encode.
     * @param expectedEncoding the NYSIIS code that each name must produce.
     */
    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    /**
     * Verifies that the phonetically related names "Brian", "Brown", and "Brun" all
     * encode to the same NYSIIS key "BRAN".  The shared code arises because NYSIIS
     * maps vowels to 'A' and collapses variant vowel sequences, causing these names
     * to converge on the same four-character representation.
     */
    @Test
    void testBran() {
        // "Brian", "Brown", and "Brun" differ only in vowel sounds; NYSIIS reduces
        // them all to the same key, demonstrating phonetic grouping.
        final String expectedCode = "BRAN";
        encodeAll(new String[] { "Brian", "Brown", "Brun" }, expectedCode);
    }
}
