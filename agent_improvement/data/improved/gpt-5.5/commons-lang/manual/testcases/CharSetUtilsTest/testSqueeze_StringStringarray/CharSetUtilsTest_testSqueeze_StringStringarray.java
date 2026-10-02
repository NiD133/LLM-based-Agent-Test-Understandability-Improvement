package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testSqueeze_StringStringarray extends AbstractLangTest {

    @Test
    void testSqueeze_StringStringarray_nullInputReturnsNull() {
        assertNull(CharSetUtils.squeeze(null, (String[]) null));
        assertNull(CharSetUtils.squeeze(null));
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, "el"));
    }

    @Test
    void testSqueeze_StringStringarray_emptyInputReturnsEmpty() {
        assertEquals("", CharSetUtils.squeeze("", (String[]) null));
        assertEquals("", CharSetUtils.squeeze(""));
        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));
    }

    @Test
    void testSqueeze_StringStringarray_noConfiguredRepeatedCharactersLeavesInputUnchanged() {
        assertEquals("hello", CharSetUtils.squeeze("hello", (String[]) null));
        assertEquals("hello", CharSetUtils.squeeze("hello"));
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));
        assertEquals("hello", CharSetUtils.squeeze("hello", "e"));
    }

    @Test
    void testSqueeze_StringStringarray_repeatedCharactersInSetAreCollapsed() {
        assertEquals("helo", CharSetUtils.squeeze("hello", "el"));
        assertEquals("fofof", CharSetUtils.squeeze("fooffooff", "of"));
        assertEquals("fof", CharSetUtils.squeeze("fooooff", "fo"));
    }
}
