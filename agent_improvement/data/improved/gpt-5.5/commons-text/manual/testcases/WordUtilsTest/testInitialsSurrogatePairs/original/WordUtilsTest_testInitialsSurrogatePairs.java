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

public class WordUtilsTest_testInitialsSurrogatePairs {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testInitialsSurrogatePairs() {
        // Tests with space as default delimiter
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01 \uD800\uDF02\uD800\uDF03"));
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01 \uD800\uDF02\uD800\uDF03", null));
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00 \uD800\uDF02 ", null));
        // Tests with UTF-16 as delimiters
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01.\uD800\uDF02\uD800\uDF03", new char[] { '.' }));
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01A\uD800\uDF02\uD800\uDF03", new char[] { 'A' }));
        // Tests with UTF-32 as delimiters
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01\uD800\uDF14\uD800\uDF02\uD800\uDF03", new char[] { '\uD800', '\uDF14' }));
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01\uD800\uDF14\uD800\uDF18\uD800\uDF02\uD800\uDF03", new char[] { '\uD800', '\uDF14', '\uD800', '\uDF18' }));
    }
}
