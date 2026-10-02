package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link NumericEntityUnescaper} safely ignores truncated numeric
 * entities that appear at the very end of the input.
 *
 * <p>A well-formed numeric entity looks like {@code &#65;} (decimal) or
 * {@code &#x41;} (hexadecimal). When the input ends before enough characters are
 * available to form a complete entity, the unescaper must not read past the end
 * of the string; it should leave the partial text untouched instead.</p>
 */
@Deprecated
public class NumericEntityUnescaperTest_testOutOfBounds extends AbstractLangTest {

    /** Each truncated prefix must be returned unchanged (no out-of-bounds read). */
    @Test
    void testOutOfBounds() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        assertEquals("Test &", unescaper.translate("Test &"),
                "Input ending in a lone '&' should be left unchanged");
        assertEquals("Test &#", unescaper.translate("Test &#"),
                "Input ending in '&#' (no digits) should be left unchanged");
        assertEquals("Test &#x", unescaper.translate("Test &#x"),
                "Input ending in '&#x' (no hex digits) should be left unchanged");
        assertEquals("Test &#X", unescaper.translate("Test &#X"),
                "Input ending in '&#X' (no hex digits) should be left unchanged");
    }
}
