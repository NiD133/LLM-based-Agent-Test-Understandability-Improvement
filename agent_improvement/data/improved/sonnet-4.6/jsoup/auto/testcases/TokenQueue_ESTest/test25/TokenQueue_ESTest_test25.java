package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test25 extends TokenQueue_ESTest_scaffolding {

    // Verifies that consuming an empty string sequence from a non-empty TokenQueue
    // succeeds without throwing an IllegalStateException, because an empty sequence
    // always matches at the current position.
    @Test(timeout = 4000)
    public void test_consumeEmptySequence_doesNotThrow() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("\"x*2MP%xE'0OK&5i:j");
        tokenQueue.consume("");
    }
}
