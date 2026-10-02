package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testKeep_StringStringarray extends AbstractLangTest {

    @Test
    void testKeep_StringStringarray() {
        assertNull(CharSetUtils.keep(null, (String[]) null));
        assertNull(CharSetUtils.keep(null));
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, "a-e"));

        assertEquals("", CharSetUtils.keep("", (String[]) null));
        assertEquals("", CharSetUtils.keep(""));
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", "a-e"));

        assertEquals("", CharSetUtils.keep("hello", (String[]) null));
        assertEquals("", CharSetUtils.keep("hello"));
        assertEquals("", CharSetUtils.keep("hello", (String) null));

        assertEquals("e", CharSetUtils.keep("hello", "a-e"));
        assertEquals("e", CharSetUtils.keep("hello", "a-e"));
        assertEquals("ell", CharSetUtils.keep("hello", "el"));
        assertEquals("hello", CharSetUtils.keep("hello", "elho"));
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));
        assertEquals("----", CharSetUtils.keep("----", "-"));
        assertEquals("ll", CharSetUtils.keep("hello", "l"));
    }
}
