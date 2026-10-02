package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test36 extends TokenQueue_ESTest_scaffolding {

    /**
     * A freshly created TokenQueue should report its full, unconsumed backing
     * data via toString(). Here the queue is built from a single-quote
     * character, so toString() should return exactly that character.
     */
    @Test(timeout = 4000)
    public void toStringReturnsUnconsumedQueueData() throws Throwable {
        TokenQueue queue = new TokenQueue("'");

        String remainingData = queue.toString();

        assertEquals("'", remainingData);
    }
}
