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

    @Test(timeout = 4000)
    public void consumeToReturnsWholeQueueWhenTerminatorIsAbsent() throws Throwable {
        String input = "'";
        String missingTerminator = "Object must not be null";

        TokenQueue queue = new TokenQueue(input);
        String consumed = queue.consumeTo(missingTerminator);

        assertEquals(input, consumed);
    }
}
