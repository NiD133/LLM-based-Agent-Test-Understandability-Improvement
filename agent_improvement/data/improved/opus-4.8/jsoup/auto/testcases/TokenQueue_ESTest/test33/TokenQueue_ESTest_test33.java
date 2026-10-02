package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test33 extends TokenQueue_ESTest_scaffolding {

    /**
     * matchChomp should report a successful match (and consume the sequence)
     * when the queue starts with the supplied string.
     */
    @Test(timeout = 4000)
    public void matchChompConsumesMatchingSequence() throws Throwable {
        TokenQueue queue = new TokenQueue("'");

        boolean matched = queue.matchChomp("'");

        assertTrue("matchChomp should return true when the queue starts with the sequence", matched);
    }
}
