package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test24 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        String queueInput = "p";
        String sequenceToConsume = "=H;zT#>e+|')Uz";
        TokenQueue queue = new TokenQueue(queueInput);

        try {
            queue.consume(sequenceToConsume);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException exception) {
            verifyException("org.jsoup.parser.TokenQueue", exception);
        }
    }
}
