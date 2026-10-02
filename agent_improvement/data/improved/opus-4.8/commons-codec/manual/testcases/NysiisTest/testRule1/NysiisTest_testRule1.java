package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests NYSIIS rule 1, which transcodes the leading characters of a name.
 */
public class NysiisTest_testRule1 extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict encoder: produces full-length codes (no 6-character cap). */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that encoding {@code input} with the non-strict encoder yields {@code expected}.
     *
     * @param input    the name to encode.
     * @param expected the expected NYSIIS encoding.
     */
    private void assertEncoding(final String input, final String expected) {
        assertEquals(expected, this.fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests rule 1: translate the first characters of a name:
     * MAC &rarr; MCC, KN &rarr; N, K &rarr; C, PH &rarr; FF, PF &rarr; FF, SCH &rarr; SSS.
     */
    @Test
    void testRule1() {
        assertEncoding("MACX", "MCX"); // MAC -> MCC
        assertEncoding("KNX", "NX");   // KN  -> N
        assertEncoding("KX", "CX");    // K   -> C
        assertEncoding("PHX", "FX");   // PH  -> FF
        assertEncoding("PFX", "FX");   // PF  -> FF
        assertEncoding("SCHX", "SX");  // SCH -> SSS
    }
}
