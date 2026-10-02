package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test02 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        TokenQueue tokenQueue0 = new TokenQueue("'");
        String string0 = tokenQueue0.chompBalanced(')', '$');
        assertEquals("'", string0);
        String string1 = tokenQueue0.consumeElementSelector();
        assertFalse(string1.equals((Object) string0));
    }
}
