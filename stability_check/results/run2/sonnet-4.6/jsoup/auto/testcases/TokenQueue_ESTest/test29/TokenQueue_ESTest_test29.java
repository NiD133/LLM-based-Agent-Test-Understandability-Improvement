package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test29 extends TokenQueue_ESTest_scaffolding {

    // Verifies that calling advance() on an empty TokenQueue is a no-op and does not throw.
    @Test(timeout = 4000)
    public void test_advance_onEmptyQueue_doesNotThrow() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("");
        tokenQueue0.advance();
    }
}
