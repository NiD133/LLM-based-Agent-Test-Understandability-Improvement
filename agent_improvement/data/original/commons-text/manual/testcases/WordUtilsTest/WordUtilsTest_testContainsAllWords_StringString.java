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

public class WordUtilsTest_testContainsAllWords_StringString {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testContainsAllWords_StringString() {
        assertFalse(WordUtils.containsAllWords(null));
        assertFalse(WordUtils.containsAllWords(null, ""));
        assertFalse(WordUtils.containsAllWords(null, "ab"));
        assertFalse(WordUtils.containsAllWords(""));
        assertFalse(WordUtils.containsAllWords("", (String) null));
        assertFalse(WordUtils.containsAllWords("", ""));
        assertFalse(WordUtils.containsAllWords("", "ab"));
        assertFalse(WordUtils.containsAllWords("foo"));
        assertFalse(WordUtils.containsAllWords("foo", (String) null));
        assertFalse(WordUtils.containsAllWords("bar", ""));
        assertFalse(WordUtils.containsAllWords("zzabyycdxx", "by"));
        assertTrue(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", null, "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("lorem ipsum null dolor sit amet", "ipsum", null, "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("ab", "b"));
        assertFalse(WordUtils.containsAllWords("ab", "z"));
        assertFalse(WordUtils.containsAllWords("ab", "["));
        assertFalse(WordUtils.containsAllWords("ab", "]"));
        assertFalse(WordUtils.containsAllWords("ab", "*"));
        assertTrue(WordUtils.containsAllWords("ab x", "ab", "x"));
    }
}
