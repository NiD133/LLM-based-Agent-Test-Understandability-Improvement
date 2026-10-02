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
     * When the terminator passed to consumeTo() never appears in the queue,
     * the whole queue content is consumed and returned.
     */
    @Test(timeout = 4000)
    public void consumeToReturnsEntireQueueWhenTerminatorIsAbsent() throws Throwable {
        TokenQueue queue = new TokenQueue("'");

        String consumed = queue.consumeTo("Object must not be null");

        assertEquals("'", consumed);
    }
}
