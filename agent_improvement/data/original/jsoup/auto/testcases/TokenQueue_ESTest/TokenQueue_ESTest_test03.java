package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test03 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("dQ_7Q");
        String string0 = tokenQueue0.consumeCssIdentifier();
        assertEquals("dQ_7Q", string0);
    }
}
