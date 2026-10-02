package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test15 extends TokenQueue_ESTest_scaffolding {

    /**
     * Verifies that chompBalanced extracts the content between a '(' opener and a 'v' closer.
     * Given "(1upvc8O", consuming from '(' to the first 'v' yields "1up".
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("(1upvc8O");
        String balanced = tokenQueue.chompBalanced('(', 'v');
        assertEquals("1up", balanced);
    }
}
