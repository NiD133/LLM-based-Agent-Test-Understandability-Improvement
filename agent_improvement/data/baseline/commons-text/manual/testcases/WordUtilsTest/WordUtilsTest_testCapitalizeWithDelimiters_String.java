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

public class WordUtilsTest_testCapitalizeWithDelimiters_String {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testCapitalizeWithDelimiters_String() {
        assertNull(WordUtils.capitalize(null, null));
        assertEquals("", WordUtils.capitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));
        char[] chars = { '-', '+', ' ', '@' };
        assertEquals("I", WordUtils.capitalize("I", chars));
        assertEquals("I", WordUtils.capitalize("i", chars));
        assertEquals("I-Am Here+123", WordUtils.capitalize("i-am here+123", chars));
        assertEquals("I Am+Here-123", WordUtils.capitalize("I Am+Here-123", chars));
        assertEquals("I+Am-HERE 123", WordUtils.capitalize("i+am-HERE 123", chars));
        assertEquals("I-AM HERE+123", WordUtils.capitalize("I-AM HERE+123", chars));
        chars = new char[] { '.' };
        assertEquals("I aM.Fine", WordUtils.capitalize("i aM.fine", chars));
        assertEquals("I Am.fine", WordUtils.capitalize("i am.fine", null));
    }
}
