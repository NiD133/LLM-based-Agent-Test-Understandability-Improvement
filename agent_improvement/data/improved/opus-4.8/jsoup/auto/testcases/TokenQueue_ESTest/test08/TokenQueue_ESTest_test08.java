package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test08 extends TokenQueue_ESTest_scaffolding {

    /**
     * escapeCssIdentifier escapes characters that are not valid in a CSS selector.
     * For the input "722m6%0O=.8DAypB":
     *   - a leading digit ('7') is escaped as its hex code point: "\37 " (note the trailing space)
     *   - the special characters '%', '=', and '.' are backslash-escaped: "\%", "\=", "\."
     *   - letters and remaining digits are left unchanged
     */
    @Test(timeout = 4000)
    public void escapeCssIdentifierEscapesLeadingDigitAndSpecialChars() throws Throwable {
        String escaped = TokenQueue.escapeCssIdentifier("722m6%0O=.8DAypB");

        assertEquals("\\37 22m6\\%0O\\=\\.8DAypB", escaped);
    }
}
