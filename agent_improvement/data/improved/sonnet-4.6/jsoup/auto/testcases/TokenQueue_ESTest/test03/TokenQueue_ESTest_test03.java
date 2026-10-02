package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test03 extends TokenQueue_ESTest_scaffolding {

    /**
     * consumeCssIdentifier() should consume and return the entire queue contents
     * when every character is a valid CSS identifier character (letters, digits,
     * hyphens, or underscores).
     */
    @Test(timeout = 4000)
    public void consumeCssIdentifier_returnsFullString_whenAllCharsAreValidCssIdentifier() throws Throwable {
        // "dQ_7Q" consists solely of valid CSS identifier characters, so the
        // entire string should be consumed and returned unchanged.
        TokenQueue tokenQueue = new TokenQueue("dQ_7Q");

        String consumed = tokenQueue.consumeCssIdentifier();

        assertEquals("Expected the full input to be consumed as a single CSS identifier",
                "dQ_7Q", consumed);
    }
}
