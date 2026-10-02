package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the NYSIIS encoding of the name "Dent".
 *
 * The NYSIIS algorithm transforms "Dent" by replacing the trailing "NT" with "D"
 * (rule 2b), producing the code "DAD". Note: the reference book "Data Quality and
 * Record Linkage Techniques" (P.121) incorrectly states the result is "DAN";
 * the correct encoding is "DAD", as verified by dropby.com.
 */
public class NysiisTest_testDad extends AbstractStringEncoderTest<Nysiis> {

    /** Encoder configured without strict mode (no 6-character length cap). */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that each input in {@code strings} encodes to {@code expectedEncoding}
     * using the strict-mode encoder returned by {@link #getStringEncoder()}.
     */
    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    @Test
    void testDad() {
        // "Dent" -> trailing "NT" is replaced by "D" (NYSIIS rule 2b) -> encodes as "DAD"
        encodeAll(new String[] { "Dent" }, "DAD");
    }
}
