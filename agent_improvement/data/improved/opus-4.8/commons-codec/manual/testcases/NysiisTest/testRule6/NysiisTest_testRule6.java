package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testRule6 extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict encoder so the result length is not capped at 6 characters. */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that encoding {@code input} with the non-strict NYSIIS encoder yields {@code expectedEncoding}.
     *
     * @param input            the String to encode.
     * @param expectedEncoding the expected NYSIIS encoding.
     */
    private void assertEncoding(final String input, final String expectedEncoding) {
        assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests rule 6: if the last characters are "AY", they are replaced with "Y".
     */
    @Test
    void testRule6() {
        // "XAY": rule 5 removes a trailing S (none here), then rule 6 turns "AY" into "Y" -> "XY".
        assertEncoding("XAY", "XY");
        // "XAYS": rule 5 removes the trailing S to give "XAY", then rule 6 turns "AY" into "Y" -> "XY".
        assertEncoding("XAYS", "XY");
    }
}
