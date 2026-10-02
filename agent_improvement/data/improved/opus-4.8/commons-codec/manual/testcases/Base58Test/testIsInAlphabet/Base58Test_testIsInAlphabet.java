package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Base58#isInAlphabet(byte)}.
 *
 * <p>The Base58 alphabet is the 58 characters
 * {@code 123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz}.
 * It deliberately omits the visually ambiguous characters {@code 0}, {@code O},
 * {@code I} and {@code l}.</p>
 */
public class Base58Test_testIsInAlphabet {

    private final Base58 base58 = new Base58();

    /**
     * Asserts that every character in the (inclusive) range {@code first..last}
     * is recognised as part of the Base58 alphabet.
     */
    private void assertRangeInAlphabet(final char first, final char last) {
        for (char c = first; c <= last; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
    }

    /**
     * Asserts that the given character is NOT part of the Base58 alphabet.
     */
    private void assertNotInAlphabet(final char c) {
        assertFalse(base58.isInAlphabet((byte) c), "char " + c);
    }

    @Test
    void testIsInAlphabet() {
        // Every character of the Base58 alphabet, split into its contiguous ranges.
        assertRangeInAlphabet('1', '9'); // digits 1-9 (0 is excluded)
        assertRangeInAlphabet('A', 'H'); // uppercase A-H
        assertRangeInAlphabet('J', 'N'); // uppercase J-N (I is excluded)
        assertRangeInAlphabet('P', 'Z'); // uppercase P-Z (O is excluded)
        assertRangeInAlphabet('a', 'k'); // lowercase a-k
        assertRangeInAlphabet('m', 'z'); // lowercase m-z (l is excluded)

        // Ambiguous characters intentionally excluded from the alphabet.
        assertNotInAlphabet('0');
        assertNotInAlphabet('O');
        assertNotInAlphabet('I');
        assertNotInAlphabet('l');

        // Byte values outside the printable alphabet range are never in the alphabet.
        assertFalse(base58.isInAlphabet((byte) -1));
        assertFalse(base58.isInAlphabet((byte) 0));
        assertFalse(base58.isInAlphabet((byte) 128));
        assertFalse(base58.isInAlphabet((byte) 255));
    }
}
