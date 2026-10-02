package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Base16Test_testIsInAlphabet {

    @Test
    void testIsInAlphabet() {
        assertBytesOutsideAlphabetAreRejected();
        assertLowerCaseAlphabet();
        assertUpperCaseAlphabet();
    }

    private void assertBytesOutsideAlphabetAreRejected() {
        final Base16 base16 = Base16.builder().setLowerCase(true).get();

        assertFalse(base16.isInAlphabet((byte) 0));
        assertFalse(base16.isInAlphabet((byte) 1));
        assertFalse(base16.isInAlphabet((byte) -1));
        assertFalse(base16.isInAlphabet((byte) -15));
        assertFalse(base16.isInAlphabet((byte) -16));
        assertFalse(base16.isInAlphabet((byte) 128));
        assertFalse(base16.isInAlphabet((byte) 255));
    }

    @SuppressWarnings("deprecation")
    private void assertLowerCaseAlphabet() {
        final Base16 base16 = new Base16(true);

        assertRangeIsInAlphabet(base16, '0', '9');
        assertRangeIsInAlphabet(base16, 'a', 'f');
        assertRangeIsNotInAlphabet(base16, 'A', 'F');

        assertFalse(base16.isInAlphabet((byte) ('0' - 1)));
        assertFalse(base16.isInAlphabet((byte) ('9' + 1)));
        assertFalse(base16.isInAlphabet((byte) ('a' - 1)));
        assertFalse(base16.isInAlphabet((byte) ('f' + 1)));
        assertFalse(base16.isInAlphabet((byte) ('z' + 1)));
    }

    @SuppressWarnings("deprecation")
    private void assertUpperCaseAlphabet() {
        final Base16 base16 = new Base16(false);

        assertRangeIsInAlphabet(base16, '0', '9');
        assertRangeIsNotInAlphabet(base16, 'a', 'f');
        assertRangeIsInAlphabet(base16, 'A', 'F');

        assertFalse(base16.isInAlphabet((byte) ('0' - 1)));
        assertFalse(base16.isInAlphabet((byte) ('9' + 1)));
        assertFalse(base16.isInAlphabet((byte) ('A' - 1)));
        assertFalse(base16.isInAlphabet((byte) ('F' + 1)));
        assertFalse(base16.isInAlphabet((byte) ('Z' + 1)));
    }

    private void assertRangeIsInAlphabet(final Base16 base16, final char first, final char last) {
        for (char current = first; current <= last; current++) {
            assertTrue(base16.isInAlphabet((byte) current));
        }
    }

    private void assertRangeIsNotInAlphabet(final Base16 base16, final char first, final char last) {
        for (char current = first; current <= last; current++) {
            assertFalse(base16.isInAlphabet((byte) current));
        }
    }
}
