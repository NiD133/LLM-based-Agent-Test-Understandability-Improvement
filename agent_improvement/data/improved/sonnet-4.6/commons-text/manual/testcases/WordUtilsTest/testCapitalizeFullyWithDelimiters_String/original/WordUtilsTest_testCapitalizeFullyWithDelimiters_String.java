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

public class WordUtilsTest_testCapitalizeFullyWithDelimiters_String {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testCapitalizeFullyWithDelimiters_String() {
        assertNull(WordUtils.capitalizeFully(null, null));
        assertEquals("", WordUtils.capitalizeFully("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalizeFully("  ", ArrayUtils.EMPTY_CHAR_ARRAY));
        char[] chars = { '-', '+', ' ', '@' };
        assertEquals("I", WordUtils.capitalizeFully("I", chars));
        assertEquals("I", WordUtils.capitalizeFully("i", chars));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("i-am here+123", chars));
        assertEquals("I Am+Here-123", WordUtils.capitalizeFully("I Am+Here-123", chars));
        assertEquals("I+Am-Here 123", WordUtils.capitalizeFully("i+am-HERE 123", chars));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("I-AM HERE+123", chars));
        chars = new char[] { '.' };
        assertEquals("I am.Fine", WordUtils.capitalizeFully("i aM.fine", chars));
        assertEquals("I Am.fine", WordUtils.capitalizeFully("i am.fine", null));
        // single word
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", null));
        // no matching delim
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", new char[] { '!' }));
    }
}
