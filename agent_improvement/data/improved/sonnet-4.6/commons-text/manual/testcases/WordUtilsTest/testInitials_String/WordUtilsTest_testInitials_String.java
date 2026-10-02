package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testInitials_String {

    @Test
    void testInitials_nullInputReturnsNull() {
        assertNull(WordUtils.initials(null));
    }

    @Test
    void testInitials_emptyAndBlankInputReturnsEmpty() {
        assertEquals("", WordUtils.initials(""));
        assertEquals("", WordUtils.initials("  "));
    }

    @Test
    void testInitials_singleWordReturnsItsFirstCharacter() {
        assertEquals("I", WordUtils.initials("I"));
        assertEquals("i", WordUtils.initials("i"));
    }

    @Test
    void testInitials_multipleWordsReturnOneInitialPerWord() {
        assertEquals("BJL", WordUtils.initials("Ben John Lee"));
    }

    @Test
    void testInitials_variousWhitespaceTypesAreAllTreatedAsWordDelimiters() {
        // spaces, newlines, and tabs all delimit words
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t"));
    }

    @Test
    void testInitials_dotWithoutSurroundingSpacesIsNotADelimiter() {
        // "J.Lee" is one word because the dot is not a whitespace delimiter
        assertEquals("BJ", WordUtils.initials("Ben J.Lee"));
    }

    @Test
    void testInitials_dotSurroundedBySpacesBecomesItsOwnWord() {
        // the isolated "." is treated as a word, so its initial is "."
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee"));
    }

    @Test
    void testInitials_digitsAreValidWordInitials() {
        assertEquals("iah1", WordUtils.initials("i am here 123"));
    }
}
