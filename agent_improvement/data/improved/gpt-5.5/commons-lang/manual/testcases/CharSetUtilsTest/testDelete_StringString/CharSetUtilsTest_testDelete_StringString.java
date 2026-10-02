package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testDelete_StringString extends AbstractLangTest {

    @Test
    void testDelete_StringString() {
        assertNullInputReturnsNull();
        assertEmptyInputStaysEmpty();
        assertBlankDeleteSetLeavesInputUnchanged();
        assertMatchingCharactersAreDeleted();
        assertMissingCharactersLeaveInputUnchanged();
    }

    private void assertNullInputReturnsNull() {
        assertNull(CharSetUtils.delete(null, (String) null));
        assertNull(CharSetUtils.delete(null, ""));
    }

    private void assertEmptyInputStaysEmpty() {
        assertEquals("", CharSetUtils.delete("", (String) null));
        assertEquals("", CharSetUtils.delete("", ""));
        assertEquals("", CharSetUtils.delete("", "a-e"));
    }

    private void assertBlankDeleteSetLeavesInputUnchanged() {
        assertEquals("hello", CharSetUtils.delete("hello", (String) null));
        assertEquals("hello", CharSetUtils.delete("hello", ""));
    }

    private void assertMatchingCharactersAreDeleted() {
        assertEquals("hllo", CharSetUtils.delete("hello", "a-e"));
        assertEquals("he", CharSetUtils.delete("hello", "l-p"));
    }

    private void assertMissingCharactersLeaveInputUnchanged() {
        assertEquals("hello", CharSetUtils.delete("hello", "z"));
    }
}
