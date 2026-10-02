package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test20 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("8#cX#pxA*");
        tokenQueue0.consumeCssIdentifier();
        // Undeclared exception!
        try {
            tokenQueue0.chompBalanced('#', 'p');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Did not find balanced marker at 'cX#pxA*'
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
