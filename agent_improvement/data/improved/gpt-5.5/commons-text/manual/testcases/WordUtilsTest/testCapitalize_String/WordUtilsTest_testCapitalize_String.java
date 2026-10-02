package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalize_String {

    @Test
    void testCapitalize_String() {
        assertNull(WordUtils.capitalize(null));

        assertCapitalizedAs("", "");
        assertCapitalizedAs("  ", "  ");
        assertCapitalizedAs("I", "I");
        assertCapitalizedAs("i", "I");
        assertCapitalizedAs("i am here 123", "I Am Here 123");
        assertCapitalizedAs("I Am Here 123", "I Am Here 123");
        assertCapitalizedAs("i am HERE 123", "I Am HERE 123");
        assertCapitalizedAs("I AM HERE 123", "I AM HERE 123");
    }

    private static void assertCapitalizedAs(final String input, final String expected) {
        assertEquals(expected, WordUtils.capitalize(input));
    }
}
