package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Base58Test_testIsInAlphabet {

    private final Base58 base58 = new Base58();

    @Test
    void testIsInAlphabet() {
        assertAlphabetContainsRange('1', '9');
        assertAlphabetContainsRange('A', 'H');
        assertAlphabetContainsRange('J', 'N');
        assertAlphabetContainsRange('P', 'Z');
        assertAlphabetContainsRange('a', 'k');
        assertAlphabetContainsRange('m', 'z');

        assertExcludedBase58CharactersAreRejected();
        assertValuesOutsideTheAlphabetTableAreRejected();
    }

    private void assertAlphabetContainsRange(final char first, final char last) {
        for (char c = first; c <= last; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
    }

    private void assertExcludedBase58CharactersAreRejected() {
        assertFalse(base58.isInAlphabet((byte) '0'), "char 0");
        assertFalse(base58.isInAlphabet((byte) 'O'), "char O");
        assertFalse(base58.isInAlphabet((byte) 'I'), "char I");
        assertFalse(base58.isInAlphabet((byte) 'l'), "char l");
    }

    private void assertValuesOutsideTheAlphabetTableAreRejected() {
        assertFalse(base58.isInAlphabet((byte) -1));
        assertFalse(base58.isInAlphabet((byte) 0));
        assertFalse(base58.isInAlphabet((byte) 128));
        assertFalse(base58.isInAlphabet((byte) 255));
    }
}
