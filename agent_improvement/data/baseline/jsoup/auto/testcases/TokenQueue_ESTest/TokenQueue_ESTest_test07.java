package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test07 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        String string0 = TokenQueue.escapeCssIdentifier("p8moD0M0$t5mTH&");
        assertEquals("p8moD0M\\7f 0\\$t5mTH\\&", string0);
    }
}
