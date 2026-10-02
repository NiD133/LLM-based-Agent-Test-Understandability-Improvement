package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test36 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_toString_returnsRemainingQueueContent() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("'");
        String remainingContent = tokenQueue.toString();
        assertEquals("toString() should return the unread content of the queue", "'", remainingContent);
    }
}
