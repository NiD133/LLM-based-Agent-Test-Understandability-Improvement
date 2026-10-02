package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test18 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        TokenQueue queueWithOnlyOpeningMarker = new TokenQueue("'");

        try {
            queueWithOnlyOpeningMarker.chompBalanced('\'', '\'');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expectedException) {
            // chompBalanced reports unbalanced markers through Validate.fail().
            verifyException("org.jsoup.helper.Validate", expectedException);
        }
    }
}
