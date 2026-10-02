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

    /**
     * Consuming the empty string from a queue always succeeds: every queue
     * trivially starts with "", so {@link TokenQueue#consume(String)} matches
     * and returns normally without throwing IllegalStateException.
     */
    @Test(timeout = 4000)
    public void consumingEmptySequenceSucceeds() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("\"x*2MP%xE'0OK&5i:j");

        tokenQueue.consume("");
    }
}
