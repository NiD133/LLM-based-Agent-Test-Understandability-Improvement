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

public class WordUtilsTest_testCapitalizeFully_String {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testCapitalizeFully_String() {
        assertNull(WordUtils.capitalizeFully(null));
        assertEquals("", WordUtils.capitalizeFully(""));
        assertEquals("  ", WordUtils.capitalizeFully("  "));
        assertEquals("I", WordUtils.capitalizeFully("I"));
        assertEquals("I", WordUtils.capitalizeFully("i"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I Am Here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am HERE 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I AM HERE 123"));
        // single word
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet"));
        assertEquals("A\tB\nC D", WordUtils.capitalizeFully("a\tb\nc d"));
        assertEquals("And \tBut \nCleat  Dome", WordUtils.capitalizeFully("and \tbut \ncleat  dome"));
        // All whitespace
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }
}
