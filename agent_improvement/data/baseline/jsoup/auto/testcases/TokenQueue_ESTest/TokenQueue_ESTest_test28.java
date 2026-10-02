package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test28 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("b\u0000L (");
        String string0 = tokenQueue0.consumeCssIdentifier();
        assertEquals("b\uFFFDL", string0);
    }
}
