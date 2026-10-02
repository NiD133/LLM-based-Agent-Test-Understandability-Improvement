package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test10 extends TokenQueue_ESTest_scaffolding {

    /**
     * Verifies that escapeCssIdentifier correctly handles an identifier that starts with
     * a hyphen followed by a digit. According to CSS escaping rules:
     *   - The leading "-" is preserved as-is.
     *   - The digit "8" immediately after "-" is escaped as a code point: "\38 " (hex 38 = ASCII '8').
     *   - Non-identifier characters like "#" and "*" are escaped with a backslash prefix.
     *
     * Input:  "-8Lc8X#GxA*"
     * Output: "-\38 Lc8X\#GxA\*"
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        String input = "-8Lc8X#GxA*";
        String escaped = TokenQueue.escapeCssIdentifier(input);
        // "-" kept, digit "8" → "\38 ", alphanumerics kept, "#" → "\#", "*" → "\*"
        assertEquals("-\\38 Lc8X\\#GxA\\*", escaped);
    }
}
