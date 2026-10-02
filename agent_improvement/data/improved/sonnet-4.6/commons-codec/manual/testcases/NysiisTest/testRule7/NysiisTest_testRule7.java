package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testRule7 extends AbstractStringEncoderTest<Nysiis> {

    // Non-strict encoder so that output length is not capped at 6.
    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Asserts that each input string (first element of each pair) encodes to the
     * expected NYSIIS value (second element) using the non-strict encoder.
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
     * Tests Rule 7: if the last character of the encoded key is 'A', remove it.
     *
     * <ul>
     *   <li>"XA"  – Rule 7 strips the trailing 'A', leaving "X".
     *       (Rule 5 is also exercised: 'X' is not 'S', so nothing changes there.)</li>
     *   <li>"XAS" – Rule 5 first strips the trailing 'S', giving the intermediate key "XA".
     *       Rule 7 then strips the trailing 'A', leaving "X".</li>
     * </ul>
     */
    @Test
    void testRule7() {
        // Rule 7 alone: trailing 'A' is removed -> "X"
        assertEncodings(new String[] { "XA", "X" });

        // Rule 5 then Rule 7: trailing 'S' removed first, then trailing 'A' removed -> "X"
        assertEncodings(new String[] { "XAS", "X" });
    }
}
