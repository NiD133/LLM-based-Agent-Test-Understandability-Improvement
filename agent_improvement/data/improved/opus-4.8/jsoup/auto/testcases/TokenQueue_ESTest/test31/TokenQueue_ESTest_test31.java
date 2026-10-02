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
     * When the terminator sequence is never found in the queue, consumeTo()
     * should drain the entire queue and return all of its contents.
     */
    @Test(timeout = 4000)
    public void consumeToReturnsWholeQueueWhenTerminatorIsAbsent() throws Throwable {
        TokenQueue queue = new TokenQueue("'");
        String absentTerminator = "Object must not be null";

        String consumed = queue.consumeTo(absentTerminator);

        assertEquals("'", consumed);
    }
}
