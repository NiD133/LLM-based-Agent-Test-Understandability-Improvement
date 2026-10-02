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

    private static final String UNBALANCED_QUEUE = "Oy0ADe'8'-Re";
    private static final char OPEN_MARKER = 'O';
    private static final char CLOSE_MARKER = 'O';

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue(UNBALANCED_QUEUE);

        try {
            tokenQueue.chompBalanced(OPEN_MARKER, CLOSE_MARKER);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
