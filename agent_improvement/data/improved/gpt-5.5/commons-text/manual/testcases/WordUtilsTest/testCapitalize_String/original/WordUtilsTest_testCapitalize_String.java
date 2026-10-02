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

public class WordUtilsTest_testCapitalize_String {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testCapitalize_String() {
        assertNull(WordUtils.capitalize(null));
        assertEquals("", WordUtils.capitalize(""));
        assertEquals("  ", WordUtils.capitalize("  "));
        assertEquals("I", WordUtils.capitalize("I"));
        assertEquals("I", WordUtils.capitalize("i"));
        assertEquals("I Am Here 123", WordUtils.capitalize("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalize("I Am Here 123"));
        assertEquals("I Am HERE 123", WordUtils.capitalize("i am HERE 123"));
        assertEquals("I AM HERE 123", WordUtils.capitalize("I AM HERE 123"));
    }
}
