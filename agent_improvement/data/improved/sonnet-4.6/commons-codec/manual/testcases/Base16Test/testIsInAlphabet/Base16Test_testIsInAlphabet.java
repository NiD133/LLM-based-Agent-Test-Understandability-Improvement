package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Base16Test_testIsInAlphabet {

    @Test
    void testIsInAlphabet_invalidBytes_returnsFalse() {
        Base16 b16 = Base16.builder().setLowerCase(true).get();
        assertFalse(b16.isInAlphabet((byte) 0),   "byte 0 is not a valid Base16 character");
        assertFalse(b16.isInAlphabet((byte) 1),   "byte 1 is not a valid Base16 character");
        assertFalse(b16.isInAlphabet((byte) -1),  "byte -1 is not a valid Base16 character");
        assertFalse(b16.isInAlphabet((byte) -15), "byte -15 is not a valid Base16 character");
        assertFalse(b16.isInAlphabet((byte) -16), "byte -16 is not a valid Base16 character");
        assertFalse(b16.isInAlphabet((byte) 128), "byte 128 is not a valid Base16 character");
        assertFalse(b16.isInAlphabet((byte) 255), "byte 255 is not a valid Base16 character");
    }

    @Test
    void testIsInAlphabet_lowerCase_acceptsDigits_0through9() {
        Base16 b16 = new Base16(true);
        for (char c = '0'; c <= '9'; c++) {
            assertTrue(b16.isInAlphabet((byte) c), "digit '" + c + "' should be in lower-case Base16 alphabet");
        }
    }

    @Test
    void testIsInAlphabet_lowerCase_acceptsLowercaseHexLetters_athrough_f() {
        Base16 b16 = new Base16(true);
        for (char c = 'a'; c <= 'f'; c++) {
            assertTrue(b16.isInAlphabet((byte) c), "lowercase '" + c + "' should be in lower-case Base16 alphabet");
        }
    }

    @Test
    void testIsInAlphabet_lowerCase_rejectsUppercaseHexLetters_Athrough_F() {
        Base16 b16 = new Base16(true);
        for (char c = 'A'; c <= 'F'; c++) {
            assertFalse(b16.isInAlphabet((byte) c), "uppercase '" + c + "' should not be in lower-case Base16 alphabet");
        }
    }

    @Test
    void testIsInAlphabet_lowerCase_rejectsBoundaryCharacters() {
        Base16 b16 = new Base16(true);
        assertFalse(b16.isInAlphabet((byte) ('0' - 1)), "byte just before '0' should not be in lower-case Base16 alphabet");
        assertFalse(b16.isInAlphabet((byte) ('9' + 1)), "byte just after '9' should not be in lower-case Base16 alphabet");
        assertFalse(b16.isInAlphabet((byte) ('a' - 1)), "byte just before 'a' should not be in lower-case Base16 alphabet");
        assertFalse(b16.isInAlphabet((byte) ('f' + 1)), "byte just after 'f' should not be in lower-case Base16 alphabet");
        assertFalse(b16.isInAlphabet((byte) ('z' + 1)), "byte just after 'z' should not be in lower-case Base16 alphabet");
    }

    @Test
    void testIsInAlphabet_upperCase_acceptsDigits_0through9() {
        Base16 b16 = new Base16(false);
        for (char c = '0'; c <= '9'; c++) {
            assertTrue(b16.isInAlphabet((byte) c), "digit '" + c + "' should be in upper-case Base16 alphabet");
        }
    }

    @Test
    void testIsInAlphabet_upperCase_rejectsLowercaseHexLetters_athrough_f() {
        Base16 b16 = new Base16(false);
        for (char c = 'a'; c <= 'f'; c++) {
            assertFalse(b16.isInAlphabet((byte) c), "lowercase '" + c + "' should not be in upper-case Base16 alphabet");
        }
    }

    @Test
    void testIsInAlphabet_upperCase_acceptsUppercaseHexLetters_Athrough_F() {
        Base16 b16 = new Base16(false);
        for (char c = 'A'; c <= 'F'; c++) {
            assertTrue(b16.isInAlphabet((byte) c), "uppercase '" + c + "' should be in upper-case Base16 alphabet");
        }
    }

    @Test
    void testIsInAlphabet_upperCase_rejectsBoundaryCharacters() {
        Base16 b16 = new Base16(false);
        assertFalse(b16.isInAlphabet((byte) ('0' - 1)), "byte just before '0' should not be in upper-case Base16 alphabet");
        assertFalse(b16.isInAlphabet((byte) ('9' + 1)), "byte just after '9' should not be in upper-case Base16 alphabet");
        assertFalse(b16.isInAlphabet((byte) ('A' - 1)), "byte just before 'A' should not be in upper-case Base16 alphabet");
        assertFalse(b16.isInAlphabet((byte) ('F' + 1)), "byte just after 'F' should not be in upper-case Base16 alphabet");
        assertFalse(b16.isInAlphabet((byte) ('Z' + 1)), "byte just after 'Z' should not be in upper-case Base16 alphabet");
    }
}
