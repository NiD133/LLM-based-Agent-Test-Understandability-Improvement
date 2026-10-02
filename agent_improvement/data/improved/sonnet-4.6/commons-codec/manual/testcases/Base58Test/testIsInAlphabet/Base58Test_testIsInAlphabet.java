package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Base58#isInAlphabet(byte)}.
 *
 * <p>The Base58 alphabet is: 123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz
 * It deliberately omits '0', 'I', 'O', and 'l' to avoid visual ambiguity.
 */
public class Base58Test_testIsInAlphabet {

    @Test
    void testIsInAlphabet() {
        final Base58 base58 = new Base58();

        // --- Valid characters in the Base58 alphabet ---

        // Digits 1-9 ('0' is excluded to avoid confusion with the letter 'O')
        for (char c = '1'; c <= '9'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }

        // Uppercase letters A-H (skips 'I' which resembles lowercase 'l' and '1')
        for (char c = 'A'; c <= 'H'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Uppercase letters J-N (resumes after the excluded 'I')
        for (char c = 'J'; c <= 'N'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Uppercase letters P-Z (skips 'O' which is easily confused with '0')
        for (char c = 'P'; c <= 'Z'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }

        // Lowercase letters a-k (skips 'l' which resembles '1' and 'I')
        for (char c = 'a'; c <= 'k'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Lowercase letters m-z (resumes after the excluded 'l')
        for (char c = 'm'; c <= 'z'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }

        // --- Characters explicitly excluded from Base58 to prevent visual confusion ---
        assertFalse(base58.isInAlphabet((byte) '0'), "char 0"); // looks like 'O'
        assertFalse(base58.isInAlphabet((byte) 'O'), "char O"); // looks like '0'
        assertFalse(base58.isInAlphabet((byte) 'I'), "char I"); // looks like 'l' and '1'
        assertFalse(base58.isInAlphabet((byte) 'l'), "char l"); // looks like 'I' and '1'

        // --- Values outside the printable ASCII range (no Base58 character can reside here) ---
        assertFalse(base58.isInAlphabet((byte) -1));
        assertFalse(base58.isInAlphabet((byte) 0));
        assertFalse(base58.isInAlphabet((byte) 128));
        assertFalse(base58.isInAlphabet((byte) 255));
    }
}
