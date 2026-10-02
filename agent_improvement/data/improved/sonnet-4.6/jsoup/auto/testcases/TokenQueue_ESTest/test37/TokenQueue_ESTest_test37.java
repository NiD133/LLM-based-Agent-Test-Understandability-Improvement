package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test37 extends TokenQueue_ESTest_scaffolding {

    // Verifies that matches(char) returns false when the queue is empty,
    // even when searching for the null character ('\0').
    @Test(timeout = 4000)
    public void test_matchesCharOnEmptyQueue_returnsFalse() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue("");
        char nullChar = '\0';

        boolean result = emptyQueue.matches(nullChar);

        assertFalse(result);
    }
}
