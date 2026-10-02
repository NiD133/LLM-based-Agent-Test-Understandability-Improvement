package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests NYSIIS rule 7: if the last character of the encoded key is {@code A}, it is removed.
 */
public class NysiisTest_testRule7 extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict encoder: produces full-length codes (no 6-character truncation). */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that {@code fullNysiis} encodes the given input to the expected NYSIIS code.
     *
     * @param input            the value to encode.
     * @param expectedEncoding the expected NYSIIS code.
     */
    private void assertEncoding(final String input, final String expectedEncoding) {
        assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests rule 7: if the last character is {@code A}, remove it.
     */
    @Test
    void testRule7() {
        // "XA": rule 5 (trailing S removal not applicable) then rule 7 drops the trailing A -> "X"
        assertEncoding("XA", "X");
        // "XAS": trailing S removed by rule 5, then the exposed trailing A removed by rule 7 -> "X"
        assertEncoding("XAS", "X");
    }
}
