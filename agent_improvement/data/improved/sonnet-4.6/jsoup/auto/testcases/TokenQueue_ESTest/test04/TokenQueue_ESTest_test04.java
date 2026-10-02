package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test04 extends TokenQueue_ESTest_scaffolding {

    /**
     * Non-ASCII characters (U+0080 and above) are valid CSS identifier code points
     * and must be returned as-is by escapeCssIdentifier (no escaping applied).
     * U+008C is a C1 control character that is >= 0x80, so it qualifies as non-ASCII
     * and should pass through unchanged.
     */
    @Test(timeout = 4000)
    public void test_escapeCssIdentifier_nonAsciiCharacter_returnedUnchanged() throws Throwable {
        // U+008C is a non-ASCII character (>= U+0080); CSS spec treats it as a valid ident code point
        String nonAsciiInput = "\u008C";

        String escaped = TokenQueue.escapeCssIdentifier(nonAsciiInput);

        // Non-ASCII characters are not escaped -- the output should equal the input
        assertEquals(nonAsciiInput, escaped);
    }
}
