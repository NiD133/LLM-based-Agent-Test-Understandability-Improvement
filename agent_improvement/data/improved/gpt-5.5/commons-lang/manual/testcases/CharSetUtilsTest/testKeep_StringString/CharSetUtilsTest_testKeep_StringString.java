package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testKeep_StringString extends AbstractLangTest {

    @Test
    void testKeep_StringString() {
        assertNull(CharSetUtils.keep(null, (String) null), "Null input remains null when the keep set is null");
        assertNull(CharSetUtils.keep(null, ""), "Null input remains null when the keep set is empty");

        assertEquals("", CharSetUtils.keep("", (String) null), "Empty input produces an empty result with a null keep set");
        assertEquals("", CharSetUtils.keep("", ""), "Empty input produces an empty result with an empty keep set");
        assertEquals("", CharSetUtils.keep("", "a-e"), "Empty input produces an empty result with a populated keep set");

        assertEquals("", CharSetUtils.keep("hello", (String) null), "A null keep set removes every character");
        assertEquals("", CharSetUtils.keep("hello", ""), "An empty keep set removes every character");
        assertEquals("", CharSetUtils.keep("hello", "xyz"), "A non-matching keep set removes every character");

        assertEquals("hello", CharSetUtils.keep("hello", "a-z"), "A range covering every character keeps the full input");
        assertEquals("hello", CharSetUtils.keep("hello", "oleh"), "A set containing every input character keeps the full input");
        assertEquals("ell", CharSetUtils.keep("hello", "el"), "Only characters included in the keep set remain");
    }
}
