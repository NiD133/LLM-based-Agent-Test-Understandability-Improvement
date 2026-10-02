package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the NYSIIS encoding of "Schmidt" to verify it produces "SNAD", not "SNAT".
 *
 * <p>The reference "Data Quality and Record Linkage Techniques" (p.121) incorrectly
 * lists the expected encoding as "SNAT". This test documents that the correct NYSIIS
 * output is "SNAD", as produced by the strict-mode encoder.</p>
 */
public class NysiisTest_testSnad extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict (full-length) NYSIIS encoder used by {@link #assertEncodings}. */
    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Asserts that each input/expected pair encodes correctly using the non-strict
     * (full-length) NYSIIS encoder.
     *
     * @param testValues pairs where {@code [i][0]} is the input name and
     *                   {@code [i][1]} is the expected NYSIIS encoding.
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
     * using the strict-mode (6-character) NYSIIS encoder.
     *
     * @param strings         the input names to encode.
     * @param expectedEncoding the NYSIIS code expected for every name in the array.
     */
    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    /**
     * Verifies that "Schmidt" encodes to "SNAD" under the strict NYSIIS algorithm.
     *
     * <p>The textbook "Data Quality and Record Linkage Techniques" (p.121) claims the
     * encoding is "SNAT", but the correct result per the NYSIIS algorithm is "SNAD".</p>
     */
    @Test
    @DisplayName("Schmidt encodes to SNAD (not SNAT as incorrectly stated on p.121 of Data Quality and Record Linkage Techniques)")
    void testSnad() {
        // Data Quality and Record Linkage Techniques P.121 claims this is SNAT,
        // but it should be SNAD
        encodeAll(new String[] { "Schmidt" }, "SNAD");
    }
}
