package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isHexDigit {
    private static final char[] HEX_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f',
        'A', 'B', 'C', 'D', 'E', 'F'
    };

    private static final char[] NON_HEX_DIGITS = {
        'g', 'G', 'ä', 'Ä', '١', '୳'
    };

    @Test
    void isHexDigit() {
        for (char hexDigit : HEX_DIGITS) {
            assertTrue(StringUtil.isHexDigit(hexDigit));
        }

        for (char nonHexDigit : NON_HEX_DIGITS) {
            assertFalse(StringUtil.isHexDigit(nonHexDigit));
        }
    }
}
