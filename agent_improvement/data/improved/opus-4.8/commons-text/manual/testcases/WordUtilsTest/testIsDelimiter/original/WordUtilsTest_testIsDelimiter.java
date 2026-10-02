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

public class WordUtilsTest_testIsDelimiter {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testIsDelimiter() {
        assertFalse(WordUtils.isDelimiter('.', null));
        assertTrue(WordUtils.isDelimiter(' ', null));
        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.' }));
        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.', '_', 'a' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.', '_', 'a', '.' }));
    }
}
