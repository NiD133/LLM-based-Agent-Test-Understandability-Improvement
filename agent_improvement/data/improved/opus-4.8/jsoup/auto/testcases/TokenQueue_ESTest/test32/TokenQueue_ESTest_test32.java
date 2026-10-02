package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test32 extends TokenQueue_ESTest_scaffolding {

    /**
     * Verifies that remainder() returns the entire queue contents unchanged,
     * including when the queue consists solely of whitespace.
     */
    @Test(timeout = 4000)
    public void remainderReturnsEntireWhitespaceQueue() throws Throwable {
        TokenQueue queue = new TokenQueue("   ");

        String remaining = queue.remainder();

        assertEquals("   ", remaining);
    }
}
