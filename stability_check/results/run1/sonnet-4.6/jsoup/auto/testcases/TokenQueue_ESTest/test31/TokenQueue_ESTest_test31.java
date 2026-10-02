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
     * When consumeTo() does not find the terminator sequence in the queue,
     * it should consume and return the entire remaining queue content.
     */
    @Test(timeout = 4000)
    public void test_consumeTo_returnsEntireContent_whenTerminatorNotFound() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("'");
        String consumed = tokenQueue.consumeTo("Object must not be null");
        assertEquals("'", consumed);
    }
}
