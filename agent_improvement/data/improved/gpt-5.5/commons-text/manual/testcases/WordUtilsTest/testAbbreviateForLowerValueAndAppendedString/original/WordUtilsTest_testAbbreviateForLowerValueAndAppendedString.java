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

public class WordUtilsTest_testAbbreviateForLowerValueAndAppendedString {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testAbbreviateForLowerValueAndAppendedString() {
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));
        assertEquals("01234-", WordUtils.abbreviate("01234 56789", 5, 10, "-"));
        assertEquals("01 23 45 67abc", WordUtils.abbreviate("01 23 45 67 89", 9, -1, "abc"));
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, ""));
    }
}
