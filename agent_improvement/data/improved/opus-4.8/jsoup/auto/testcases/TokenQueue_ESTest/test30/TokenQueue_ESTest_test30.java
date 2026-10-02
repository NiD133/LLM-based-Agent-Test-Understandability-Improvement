package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test30 extends TokenQueue_ESTest_scaffolding {

    /**
     * An empty queue should match the empty sequence, since every queue
     * (case-insensitively) starts with "".
     */
    @Test(timeout = 4000)
    public void emptyQueueMatchesEmptySequence() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue("");

        boolean matchesEmptySequence = emptyQueue.matches("");

        assertTrue(matchesEmptySequence);
    }
}
