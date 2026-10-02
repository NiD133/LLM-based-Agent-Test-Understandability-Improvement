package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test24 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("p");
        // Undeclared exception!
        try {
            tokenQueue0.consume("=H;zT#>e+|')Uz");
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Queue did not match expected sequence
            //
            verifyException("org.jsoup.parser.TokenQueue", e);
        }
    }
}
