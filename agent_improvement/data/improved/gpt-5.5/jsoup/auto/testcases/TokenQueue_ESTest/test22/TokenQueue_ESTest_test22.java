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
        TokenQueue queue = new TokenQueue("p h7u#");
        String[] terminators = new String[1];

        queue.advance();
        terminators[0] = "p h7u#";

        String consumedBeforeTerminator = queue.consumeToAny(terminators);

        assertEquals(" h7u#", consumedBeforeTerminator);
    }
}
