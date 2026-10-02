package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test37 extends TokenQueue_ESTest_scaffolding {

    private static final char DELETE_CONTROL_CHARACTER = '\u007F';

    @Test(timeout = 4000)
    public void emptyQueueDoesNotMatchDeleteControlCharacter() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue("");

        boolean matchesDeleteControlCharacter = emptyQueue.matches(DELETE_CONTROL_CHARACTER);

        assertFalse(matchesDeleteControlCharacter);
    }
}
