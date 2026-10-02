package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testUncapitalize_String {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testUncapitalize_String() {
        assertNull(WordUtils.uncapitalize(null));
        assertEquals("", WordUtils.uncapitalize(""));
        assertEquals("  ", WordUtils.uncapitalize("  "));
        assertEquals("i", WordUtils.uncapitalize("I"));
        assertEquals("i", WordUtils.uncapitalize("i"));
        assertEquals("i am here 123", WordUtils.uncapitalize("i am here 123"));
        assertEquals("i am here 123", WordUtils.uncapitalize("I Am Here 123"));
        assertEquals("i am hERE 123", WordUtils.uncapitalize("i am HERE 123"));
        assertEquals("i aM hERE 123", WordUtils.uncapitalize("I AM HERE 123"));
        assertEquals("a\tb\nc d", WordUtils.uncapitalize("A\tB\nC D"));
        assertEquals("and \tbut \ncLEAT  dome", WordUtils.uncapitalize("And \tBut \nCLEAT  Dome"));
        // All whitespace
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }
}
