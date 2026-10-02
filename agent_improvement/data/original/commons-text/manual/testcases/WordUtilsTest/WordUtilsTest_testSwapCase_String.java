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

public class WordUtilsTest_testSwapCase_String {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testSwapCase_String() {
        assertNull(WordUtils.swapCase(null));
        assertEquals("", WordUtils.swapCase(""));
        assertEquals("  ", WordUtils.swapCase("  "));
        assertEquals("i", WordUtils.swapCase("I"));
        assertEquals("I", WordUtils.swapCase("i"));
        assertEquals("I AM HERE 123", WordUtils.swapCase("i am here 123"));
        assertEquals("i aM hERE 123", WordUtils.swapCase("I Am Here 123"));
        assertEquals("I AM here 123", WordUtils.swapCase("i am HERE 123"));
        assertEquals("i am here 123", WordUtils.swapCase("I AM HERE 123"));
        final String test = "This String contains a TitleCase character: \u01C8";
        final String expect = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: \u01C9";
        assertEquals(expect, WordUtils.swapCase(test));
    }
}
