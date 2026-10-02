package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testSqueeze_StringString extends AbstractLangTest {

    @Test
    void testSqueeze_StringString() {
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, ""));

        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", ""));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));

        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        assertEquals("hello", CharSetUtils.squeeze("hello", ""));
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));

        assertEquals("helo", CharSetUtils.squeeze("hello", "l-p"));
        assertEquals("heloo", CharSetUtils.squeeze("helloo", "l"));
        assertEquals("hello", CharSetUtils.squeeze("helloo", "^l"));
    }
}
