package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isHexDigit {

    /** All characters that make up a valid hexadecimal digit: 0-9, a-f, and A-F. */
    private static final String HEX_DIGITS = "0123456789abcdefABCDEF";

    /**
     * Characters that look digit-like or letter-like but are NOT valid hex digits:
     * 'g'/'G' fall just outside the a-f/A-F range, and the remaining characters are
     * non-ASCII (accented letters and other scripts' digits).
     */
    private static final String NON_HEX_DIGITS = "gGäÄ١୳";

    @Test
    void isHexDigit() {
        for (char c : HEX_DIGITS.toCharArray())
            assertTrue(StringUtil.isHexDigit(c), "Expected '" + c + "' to be a hex digit");

        for (char c : NON_HEX_DIGITS.toCharArray())
            assertFalse(StringUtil.isHexDigit(c), "Expected '" + c + "' to not be a hex digit");
    }
}
