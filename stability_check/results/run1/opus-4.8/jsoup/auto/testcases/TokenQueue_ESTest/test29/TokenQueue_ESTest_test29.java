package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test29 extends TokenQueue_ESTest_scaffolding {

    /**
     * Calling advance() on a queue backed by an empty string is a no-op and
     * completes without throwing, since advance() skips the underlying reader
     * when the queue is already empty.
     */
    @Test(timeout = 4000)
    public void advanceOnEmptyQueueDoesNothing() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue("");

        emptyQueue.advance();
    }
}
