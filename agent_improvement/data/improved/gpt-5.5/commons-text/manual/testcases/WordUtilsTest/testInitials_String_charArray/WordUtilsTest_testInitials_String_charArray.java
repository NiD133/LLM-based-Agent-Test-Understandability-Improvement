package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testInitials_String_charArray {

    @Test
    void testInitials_String_charArray() {
        assertInitialsUsingDefaultWhitespaceDelimiters();
        assertInitialsUsingNoDelimiters();
        assertInitialsUsingSpaceDelimiter();
        assertInitialsUsingSpaceAndPeriodDelimiters();
        assertInitialsUsingSpacePeriodAndApostropheDelimiters();
        assertInitialsUsingLettersAndDigitAsDelimiters();
    }

    private void assertInitialsUsingDefaultWhitespaceDelimiters() {
        final char[] delimiters = null;

        assertInitials(null, null, delimiters);
        assertInitials("", "", delimiters);
        assertInitials("", "  ", delimiters);
        assertInitials("I", "I", delimiters);
        assertInitials("i", "i", delimiters);
        assertInitials("S", "SJC", delimiters);
        assertInitials("BJL", "Ben John Lee", delimiters);
        assertInitials("BJL", "   Ben \n   John\tLee\t", delimiters);
        assertInitials("BJ", "Ben J.Lee", delimiters);
        assertInitials("BJ.L", " Ben   John  . Lee", delimiters);
        assertInitials("KO", "Kay O'Murphy", delimiters);
        assertInitials("iah1", "i am here 123", delimiters);
    }

    private void assertInitialsUsingNoDelimiters() {
        final char[] delimiters = ArrayUtils.EMPTY_CHAR_ARRAY;

        assertInitials(null, null, delimiters);
        assertInitials("", "", delimiters);
        assertInitials("", "  ", delimiters);
        assertInitials("", "I", delimiters);
        assertInitials("", "i", delimiters);
        assertInitials("", "SJC", delimiters);
        assertInitials("", "Ben John Lee", delimiters);
        assertInitials("", "   Ben \n   John\tLee\t", delimiters);
        assertInitials("", "Ben J.Lee", delimiters);
        assertInitials("", " Ben   John  . Lee", delimiters);
        assertInitials("", "Kay O'Murphy", delimiters);
        assertInitials("", "i am here 123", delimiters);
    }

    private void assertInitialsUsingSpaceDelimiter() {
        final char[] delimiters = " ".toCharArray();

        assertInitials(null, null, delimiters);
        assertInitials("", "", delimiters);
        assertInitials("", "  ", delimiters);
        assertInitials("I", "I", delimiters);
        assertInitials("i", "i", delimiters);
        assertInitials("S", "SJC", delimiters);
        assertInitials("BJL", "Ben John Lee", delimiters);
        assertInitials("BJ", "Ben J.Lee", delimiters);
        assertInitials("B\nJ", "   Ben \n   John\tLee\t", delimiters);
        assertInitials("BJ.L", " Ben   John  . Lee", delimiters);
        assertInitials("KO", "Kay O'Murphy", delimiters);
        assertInitials("iah1", "i am here 123", delimiters);
    }

    private void assertInitialsUsingSpaceAndPeriodDelimiters() {
        final char[] delimiters = " .".toCharArray();

        assertInitials(null, null, delimiters);
        assertInitials("", "", delimiters);
        assertInitials("", "  ", delimiters);
        assertInitials("I", "I", delimiters);
        assertInitials("i", "i", delimiters);
        assertInitials("S", "SJC", delimiters);
        assertInitials("BJL", "Ben John Lee", delimiters);
        assertInitials("BJL", "Ben J.Lee", delimiters);
        assertInitials("BJL", " Ben   John  . Lee", delimiters);
        assertInitials("KO", "Kay O'Murphy", delimiters);
        assertInitials("iah1", "i am here 123", delimiters);
    }

    private void assertInitialsUsingSpacePeriodAndApostropheDelimiters() {
        final char[] delimiters = " .'".toCharArray();

        assertInitials(null, null, delimiters);
        assertInitials("", "", delimiters);
        assertInitials("", "  ", delimiters);
        assertInitials("I", "I", delimiters);
        assertInitials("i", "i", delimiters);
        assertInitials("S", "SJC", delimiters);
        assertInitials("BJL", "Ben John Lee", delimiters);
        assertInitials("BJL", "Ben J.Lee", delimiters);
        assertInitials("BJL", " Ben   John  . Lee", delimiters);
        assertInitials("KOM", "Kay O'Murphy", delimiters);
        assertInitials("iah1", "i am here 123", delimiters);
    }

    private void assertInitialsUsingLettersAndDigitAsDelimiters() {
        final char[] delimiters = "SIJo1".toCharArray();

        assertInitials(null, null, delimiters);
        assertInitials("", "", delimiters);
        assertInitials(" ", "  ", delimiters);
        assertInitials("", "I", delimiters);
        assertInitials("i", "i", delimiters);
        assertInitials("C", "SJC", delimiters);
        assertInitials("Bh", "Ben John Lee", delimiters);
        assertInitials("B.", "Ben J.Lee", delimiters);
        assertInitials(" h", " Ben   John  . Lee", delimiters);
        assertInitials("K", "Kay O'Murphy", delimiters);
        assertInitials("i2", "i am here 123", delimiters);
    }

    private void assertInitials(final String expected, final String input, final char[] delimiters) {
        final String actual = WordUtils.initials(input, delimiters);
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected, actual);
        }
    }
}
