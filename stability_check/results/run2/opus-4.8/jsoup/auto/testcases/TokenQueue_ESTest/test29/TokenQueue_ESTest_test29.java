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
     * Calling advance() on an empty queue is a no-op and must not throw.
     * advance() guards on isEmpty(), so it should silently do nothing here.
     */
    @Test(timeout = 4000)
    public void advanceOnEmptyQueueDoesNothing() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue("");

        // No assertion needed: the test passes as long as this call returns
        // without throwing on the empty queue.
        emptyQueue.advance();
    }
}
