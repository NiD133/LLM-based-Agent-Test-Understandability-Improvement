package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test26 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("722m6%0O=.8DAypB");
        boolean boolean0 = tokenQueue0.matchChomp('7');
        assertTrue(boolean0);
    }
}
