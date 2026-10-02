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

    private static final String EMPTY_INPUT = "";
    private static final String EMPTY_SEQUENCE = "";

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue(EMPTY_INPUT);

        boolean matchesEmptySequence = emptyQueue.matches(EMPTY_SEQUENCE);

        assertTrue(matchesEmptySequence);
    }
}
