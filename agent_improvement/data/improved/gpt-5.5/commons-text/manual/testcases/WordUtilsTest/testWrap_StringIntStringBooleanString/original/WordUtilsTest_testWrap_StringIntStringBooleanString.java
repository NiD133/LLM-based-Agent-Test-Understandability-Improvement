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

public class WordUtilsTest_testWrap_StringIntStringBooleanString {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testWrap_StringIntStringBooleanString() {
        // no changes test
        String input = "flammable/inflammable";
        String expected = "flammable/inflammable";
        assertEquals(expected, WordUtils.wrap(input, 30, "\n", false, "/"));
        // wrap on / and small width
        expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false, "/"));
        // wrap long words on / 1
        expected = "flammable\ninflammab\nle";
        assertEquals(expected, WordUtils.wrap(input, 9, "\n", true, "/"));
        // wrap long words on / 2
        expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 15, "\n", true, "/"));
        // wrap long words on / 3
        input = "flammableinflammable";
        expected = "flammableinflam\nmable";
        assertEquals(expected, WordUtils.wrap(input, 15, "\n", true, "/"));
    }
}
