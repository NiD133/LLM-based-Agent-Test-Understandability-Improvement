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

    // An empty TokenQueue should match an empty string pattern, because the
    // queue position is at the start and an empty sequence requires no characters to match.
    @Test(timeout = 4000)
    public void test_emptyQueueMatchesEmptyString() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue("");
        boolean matchesEmptyPattern = emptyQueue.matches("");
        assertTrue(matchesEmptyPattern);
    }
}
