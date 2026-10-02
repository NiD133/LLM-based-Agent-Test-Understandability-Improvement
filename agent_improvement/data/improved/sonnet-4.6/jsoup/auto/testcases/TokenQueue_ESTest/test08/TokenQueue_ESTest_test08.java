package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test08 extends TokenQueue_ESTest_scaffolding {

    /**
     * Verifies that escapeCssIdentifier:
     *  - escapes a leading digit ('7' → "\37 " using its hex code point)
     *  - escapes CSS-special punctuation ('%' → "\%", '=' → "\=", '.' → "\.")
     *  - leaves valid CSS identifier characters (letters and digits after position 0) unchanged
     */
    @Test(timeout = 4000)
    public void test08_escapeCssIdentifier_escapesLeadingDigitAndSpecialPunctuation() throws Throwable {
        // Input starts with digit '7' and contains special chars '%', '=', '.'
        String input = "722m6%0O=.8DAypB";

        String escaped = TokenQueue.escapeCssIdentifier(input);

        // '7' (U+0037) → "\37 " (hex code-point escape); '%', '=', '.' → backslash-escaped
        assertEquals("\\37 22m6\\%0O\\=\\.8DAypB", escaped);
    }
}
