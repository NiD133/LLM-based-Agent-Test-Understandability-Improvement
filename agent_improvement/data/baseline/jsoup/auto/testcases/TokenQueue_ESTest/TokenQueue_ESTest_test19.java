package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test19 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("Oy0ADe'8'-Re");
        // Undeclared exception!
        try {
            tokenQueue0.chompBalanced('O', 'O');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Did not find balanced marker at 'y0ADe'8'-Re'
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
