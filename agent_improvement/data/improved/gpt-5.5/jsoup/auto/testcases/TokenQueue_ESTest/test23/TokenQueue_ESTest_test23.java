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

    private static final String INPUT_TO_CONSUME = "722m6%0O=.8DAypB";
    private static final String[] NO_TERMINATORS = new String[0];

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        TokenQueue queue = new TokenQueue(INPUT_TO_CONSUME);
        queue.consumeToAny(NO_TERMINATORS);

        // Undeclared exception!
        try {
            queue.consumeCssIdentifier();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // CSS identifier expected, but end of input found
            //
            verifyException("org.jsoup.parser.TokenQueue", e);
        }
    }
}
