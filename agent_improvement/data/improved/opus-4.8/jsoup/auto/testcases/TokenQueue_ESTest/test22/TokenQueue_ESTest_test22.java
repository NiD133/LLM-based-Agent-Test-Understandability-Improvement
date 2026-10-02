package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test22 extends TokenQueue_ESTest_scaffolding {

    /**
     * After advancing past the first character, consumeToAny should drain the rest
     * of the queue when no terminator matches the remaining content.
     *
     * Queue starts as "p h7u#"; advance() drops the leading 'p', leaving " h7u#".
     * The only terminator is the original full string "p h7u#", which never matches
     * the shorter remainder, so consumeToAny consumes everything that is left.
     */
    @Test(timeout = 4000)
    public void consumeToAnyDrainsQueueWhenNoTerminatorMatches() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("p h7u#");

        tokenQueue.advance(); // drop the leading 'p', leaving " h7u#"

        String[] terminators = new String[] { "p h7u#" };
        String consumed = tokenQueue.consumeToAny(terminators);

        assertEquals(" h7u#", consumed);
    }
}
