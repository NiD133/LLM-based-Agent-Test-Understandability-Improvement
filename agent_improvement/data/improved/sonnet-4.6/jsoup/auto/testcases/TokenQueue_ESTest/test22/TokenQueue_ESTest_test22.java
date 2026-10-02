package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test22 extends TokenQueue_ESTest_scaffolding {

    // consumeToAny returns the full remainder when the terminator sequence is not present in the queue
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        // Queue initially contains "p h7u#"; after advance() the first char 'p' is dropped,
        // leaving " h7u#" as the remaining content.
        TokenQueue queue = new TokenQueue("p h7u#");
        queue.advance();

        // Use the original full string as the terminator — it will never match the
        // remaining content (" h7u#"), so consumeToAny consumes everything.
        String[] terminators = new String[] { "p h7u#" };
        String consumed = queue.consumeToAny(terminators);

        assertEquals(" h7u#", consumed);
    }
}
