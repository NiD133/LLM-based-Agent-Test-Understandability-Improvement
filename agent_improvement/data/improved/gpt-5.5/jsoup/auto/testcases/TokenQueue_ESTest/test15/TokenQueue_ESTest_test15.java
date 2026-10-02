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

    private static final String QUEUE_INPUT = "(1upvc8O";
    private static final char OPEN_MARKER = '(';
    private static final char CLOSE_MARKER = 'v';
    private static final String BALANCED_CONTENT = "1up";

    @Test(timeout = 4000)
    public void test15_chompBalancedConsumesContentBetweenCustomMarkers() throws Throwable {
        TokenQueue queue = new TokenQueue(QUEUE_INPUT);

        String balancedContent = queue.chompBalanced(OPEN_MARKER, CLOSE_MARKER);

        assertEquals(BALANCED_CONTENT, balancedContent);
    }
}
