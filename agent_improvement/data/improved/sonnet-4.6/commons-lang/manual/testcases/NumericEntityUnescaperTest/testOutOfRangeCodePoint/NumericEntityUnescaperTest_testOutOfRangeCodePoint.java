package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that NumericEntityUnescaper leaves numeric HTML entities unchanged
 * when the encoded code point exceeds the Unicode maximum (0x10FFFF / 1114111).
 */
@Deprecated
public class NumericEntityUnescaperTest_testOutOfRangeCodePoint extends AbstractLangTest {

    // The first code point beyond the Unicode maximum (Character.MAX_CODE_POINT = 0x10FFFF)
    private static final String HEX_ONE_ABOVE_MAX   = "&#x110000;";   // 0x110000 = 1114112
    private static final String DEC_ONE_ABOVE_MAX   = "&#1114112;";   // same value, decimal form
    private static final String HEX_INT_MAX         = "&#x7FFFFFFF;"; // Integer.MAX_VALUE in hex, far above max

    @Test
    void testOutOfRangeCodePoint() {
        final NumericEntityUnescaper neu = new NumericEntityUnescaper();

        // Out-of-range entities must be passed through untouched (returned as-is)
        assertEquals(HEX_ONE_ABOVE_MAX, neu.translate(HEX_ONE_ABOVE_MAX),
                "Failed to ignore code point above 0x10FFFF");
        assertEquals(DEC_ONE_ABOVE_MAX, neu.translate(DEC_ONE_ABOVE_MAX),
                "Failed to ignore code point above 0x10FFFF");
        assertEquals(HEX_INT_MAX, neu.translate(HEX_INT_MAX),
                "Failed to ignore code point above 0x10FFFF");
    }
}
