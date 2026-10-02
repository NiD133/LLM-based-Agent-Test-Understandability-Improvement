package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testInitials_String_charArray {

    @Test
    void testInitials_nullDelimiters_usesAllWhitespace() {
        char[] array = null;
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("I", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("S", WordUtils.initials("SJC", array));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", array));
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t", array));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee", array));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("iah1", WordUtils.initials("i am here 123", array));
    }

    @Test
    void testInitials_emptyDelimiters_alwaysReturnsEmpty() {
        char[] array = ArrayUtils.EMPTY_CHAR_ARRAY;
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("", WordUtils.initials("I", array));
        assertEquals("", WordUtils.initials("i", array));
        assertEquals("", WordUtils.initials("SJC", array));
        assertEquals("", WordUtils.initials("Ben John Lee", array));
        assertEquals("", WordUtils.initials("   Ben \n   John\tLee\t", array));
        assertEquals("", WordUtils.initials("Ben J.Lee", array));
        assertEquals("", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("", WordUtils.initials("i am here 123", array));
    }

    @Test
    void testInitials_spaceDelimiter_onlySpaceSplitsWords() {
        char[] array = " ".toCharArray();
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("I", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("S", WordUtils.initials("SJC", array));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", array));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee", array));
        assertEquals("B\nJ", WordUtils.initials("   Ben \n   John\tLee\t", array));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("iah1", WordUtils.initials("i am here 123", array));
    }

    @Test
    void testInitials_spaceAndDotDelimiters_dotAlsoSplitsWords() {
        char[] array = " .".toCharArray();
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("I", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("S", WordUtils.initials("SJC", array));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", array));
        assertEquals("BJL", WordUtils.initials("Ben J.Lee", array));
        assertEquals("BJL", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("iah1", WordUtils.initials("i am here 123", array));
    }

    @Test
    void testInitials_spaceAndDotAndApostropheDelimiters_apostropheAlsoSplitsWords() {
        char[] array = " .'".toCharArray();
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("I", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("S", WordUtils.initials("SJC", array));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", array));
        assertEquals("BJL", WordUtils.initials("Ben J.Lee", array));
        assertEquals("BJL", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("KOM", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("iah1", WordUtils.initials("i am here 123", array));
    }

    @Test
    void testInitials_letterAndDigitDelimiters_splitOnThoseCharacters() {
        char[] array = "SIJo1".toCharArray();
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals(" ", WordUtils.initials("  ", array));
        assertEquals("", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("C", WordUtils.initials("SJC", array));
        assertEquals("Bh", WordUtils.initials("Ben John Lee", array));
        assertEquals("B.", WordUtils.initials("Ben J.Lee", array));
        assertEquals(" h", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("K", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("i2", WordUtils.initials("i am here 123", array));
    }
}
