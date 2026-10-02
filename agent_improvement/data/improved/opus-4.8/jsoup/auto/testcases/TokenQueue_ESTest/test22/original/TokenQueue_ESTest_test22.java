package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test22 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("p h7u#");
        String[] stringArray0 = new String[1];
        tokenQueue0.advance();
        stringArray0[0] = "p h7u#";
        String string0 = tokenQueue0.consumeToAny(stringArray0);
        assertEquals(" h7u#", string0);
    }
}
