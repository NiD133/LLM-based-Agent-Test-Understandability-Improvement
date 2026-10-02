package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testInitials_String {

    @Test
    void testInitials_String() {
        assertNull(WordUtils.initials(null));

        assertEquals("", WordUtils.initials(""));
        assertEquals("", WordUtils.initials("  "));

        assertEquals("I", WordUtils.initials("I"));
        assertEquals("i", WordUtils.initials("i"));

        assertEquals("BJL", WordUtils.initials("Ben John Lee"));
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t"));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee"));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee"));
        assertEquals("iah1", WordUtils.initials("i am here 123"));
    }
}
