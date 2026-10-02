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

    // Verifies that advance() on an empty queue is a safe no-op: the queue
    // stays empty and no exception is thrown.
    @Test(timeout = 4000)
    public void test_advance_onEmptyQueue_isNoOp() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue("");

        emptyQueue.advance(); // should not throw

        assertTrue("Queue should still be empty after advance on empty input", emptyQueue.isEmpty());
    }
}
