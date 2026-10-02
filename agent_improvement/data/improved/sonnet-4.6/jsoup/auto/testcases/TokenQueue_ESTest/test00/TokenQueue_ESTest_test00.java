package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test00 extends TokenQueue_ESTest_scaffolding {

    // consumeElementSelector stops at ')' because it is not a valid element-selector character;
    // the leading digit '8' is the only consumed token.
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        TokenQueue queue = new TokenQueue("8)cpsx*");
        String consumedSelector = queue.consumeElementSelector();
        assertEquals("8", consumedSelector);
    }
}
