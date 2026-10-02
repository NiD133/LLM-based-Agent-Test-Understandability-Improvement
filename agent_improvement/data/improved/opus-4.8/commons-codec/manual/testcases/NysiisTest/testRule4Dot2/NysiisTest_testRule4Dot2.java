package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests NYSIIS encoding rule 4.2, which transcodes single characters in the
 * middle of a name as follows:
 * <ul>
 *   <li>Q &rarr; G</li>
 *   <li>Z &rarr; S</li>
 *   <li>M &rarr; N</li>
 * </ul>
 * The non-strict encoder is used so that the encoded result keeps its full
 * (unbounded) length rather than being truncated to 6 characters.
 */
public class NysiisTest_testRule4Dot2 extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict encoder: results are not truncated to the strict 6-character limit. */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that the non-strict encoder maps {@code input} to {@code expectedEncoding}.
     *
     * @param input            the name to encode.
     * @param expectedEncoding the expected NYSIIS code.
     */
    private void assertEncoding(final String input, final String expectedEncoding) {
        assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests rule 4.2: Q &rarr; G, Z &rarr; S, M &rarr; N.
     */
    @Test
    void testRule4Dot2() {
        assertEncoding("XQ", "XG"); // Q -> G
        assertEncoding("XZ", "X");  // Z -> S, trailing S then removed
        assertEncoding("XM", "XN"); // M -> N
    }
}
