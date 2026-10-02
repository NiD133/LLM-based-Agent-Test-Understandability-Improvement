package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Base16#isInAlphabet(byte)} for both the lower-case and upper-case Base16 alphabets.
 *
 * <p>
 * The Base16 alphabet consists of the digits {@code '0'..'9'} plus six letters. Which six letters are
 * accepted depends on the configured case: {@code 'a'..'f'} for the lower-case codec and {@code 'A'..'F'}
 * for the upper-case codec. Everything else is rejected.
 * </p>
 */
public class Base16Test_testIsInAlphabet {

    /**
     * Asserts that every character in the inclusive range {@code [first, last]} produces {@code expected}
     * when passed to {@link Base16#isInAlphabet(byte)}.
     */
    private static void assertRange(final Base16 codec, final char first, final char last, final boolean expected) {
        for (char c = first; c <= last; c++) {
            assertInAlphabet(expected, codec, c);
        }
    }

    /**
     * Asserts that a single character produces {@code expected} when passed to
     * {@link Base16#isInAlphabet(byte)}.
     */
    private static void assertInAlphabet(final boolean expected, final Base16 codec, final char c) {
        if (expected) {
            assertTrue(codec.isInAlphabet((byte) c), "expected '" + c + "' to be in the alphabet");
        } else {
            assertFalse(codec.isInAlphabet((byte) c), "expected '" + c + "' to be rejected");
        }
    }

    @Test
    void testIsInAlphabet() {
        // Bytes that are out of range (negative, or >= the decode-table length) are never in the alphabet.
        final Base16 outOfRange = Base16.builder().setLowerCase(true).get();
        assertFalse(outOfRange.isInAlphabet((byte) 0));
        assertFalse(outOfRange.isInAlphabet((byte) 1));
        assertFalse(outOfRange.isInAlphabet((byte) -1));
        assertFalse(outOfRange.isInAlphabet((byte) -15));
        assertFalse(outOfRange.isInAlphabet((byte) -16));
        assertFalse(outOfRange.isInAlphabet((byte) 128));
        assertFalse(outOfRange.isInAlphabet((byte) 255));

        // Lower-case codec: digits and 'a'..'f' are accepted; upper-case letters are rejected.
        final Base16 lowerCase = new Base16(true);
        assertRange(lowerCase, '0', '9', true);
        assertRange(lowerCase, 'a', 'f', true);
        assertRange(lowerCase, 'A', 'F', false);
        // Characters just outside each accepted range are rejected.
        assertInAlphabet(false,lowerCase, (char) ('0' - 1));
        assertInAlphabet(false,lowerCase, (char) ('9' + 1));
        assertInAlphabet(false,lowerCase, (char) ('a' - 1));
        assertInAlphabet(false,lowerCase, (char) ('f' + 1));
        assertInAlphabet(false,lowerCase, (char) ('z' + 1));

        // Upper-case codec: digits and 'A'..'F' are accepted; lower-case letters are rejected.
        final Base16 upperCase = new Base16(false);
        assertRange(upperCase, '0', '9', true);
        assertRange(upperCase, 'a', 'f', false);
        assertRange(upperCase, 'A', 'F', true);
        // Characters just outside each accepted range are rejected.
        assertInAlphabet(false,upperCase, (char) ('0' - 1));
        assertInAlphabet(false,upperCase, (char) ('9' + 1));
        assertInAlphabet(false,upperCase, (char) ('A' - 1));
        assertInAlphabet(false,upperCase, (char) ('F' + 1));
        assertInAlphabet(false,upperCase, (char) ('Z' + 1));
    }
}
