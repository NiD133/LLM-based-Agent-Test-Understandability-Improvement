package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test23 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("722m6%0O=.8DAypB");
        String[] stringArray0 = new String[0];
        tokenQueue0.consumeToAny(stringArray0);
        // Undeclared exception!
        try {
            tokenQueue0.consumeCssIdentifier();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // CSS identifier expected, but end of input found
            //
            verifyException("org.jsoup.parser.TokenQueue", e);
        }
    }
}
