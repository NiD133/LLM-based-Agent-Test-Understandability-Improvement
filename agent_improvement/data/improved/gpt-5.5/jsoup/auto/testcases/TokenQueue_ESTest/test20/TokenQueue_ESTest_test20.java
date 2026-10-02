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

    private static final String QUEUE_WITH_UNBALANCED_MARKER = "8#cX#pxA*";
    private static final char OPEN_MARKER = '#';
    private static final char CLOSE_MARKER = 'p';

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        TokenQueue queue = new TokenQueue(QUEUE_WITH_UNBALANCED_MARKER);

        queue.consumeCssIdentifier();

        try {
            queue.chompBalanced(OPEN_MARKER, CLOSE_MARKER);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException("org.jsoup.helper.Validate", exception);
        }
    }
}
