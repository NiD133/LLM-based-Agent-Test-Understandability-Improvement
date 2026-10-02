package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test31 extends TokenQueue_ESTest_scaffolding {

    /**
     * When consumeTo() is called with a sequence that does not appear in the queue,
     * it should consume and return everything in the queue up to the end.
     */
    @Test(timeout = 4000)
    public void test_consumeTo_returnsEntireQueue_whenTargetSequenceNotFound() throws Throwable {
        String queueContent = "'";
        TokenQueue tokenQueue = new TokenQueue(queueContent);

        String absentTarget = "Object must not be null";
        String consumed = tokenQueue.consumeTo(absentTarget);

        assertEquals(queueContent, consumed);
    }
}
