package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests NYSIIS rule 4.1, which transcodes vowel sequences in the middle of a name:
 * the pair "EV" becomes "AF", while any single vowel (A, E, I, O, U) becomes "A".
 *
 * <p>Each case below uses the form {@code X<vowels>X}: a leading and trailing "X" frame
 * the vowel(s) under test so the rule is exercised on characters that are neither the
 * first nor the last of the name.</p>
 */
public class NysiisTest_testRule4Dot1 extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict encoder so encodings are not truncated to the 6-character strict length. */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that encoding {@code input} with the non-strict NYSIIS encoder yields {@code expected}.
     */
    private void assertEncodesTo(final String input, final String expected) {
        assertEquals(expected, fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests rule 4.1: EV &rarr; AF, otherwise A, E, I, O, U &rarr; A.
     */
    @Test
    void testRule4Dot1() {
        // "EV" -> "AF"
        assertEncodesTo("XEV", "XAF");

        // Each single vowel -> "A"
        assertEncodesTo("XAX", "XAX");
        assertEncodesTo("XEX", "XAX");
        assertEncodesTo("XIX", "XAX");
        assertEncodesTo("XOX", "XAX");
        assertEncodesTo("XUX", "XAX");
    }
}
