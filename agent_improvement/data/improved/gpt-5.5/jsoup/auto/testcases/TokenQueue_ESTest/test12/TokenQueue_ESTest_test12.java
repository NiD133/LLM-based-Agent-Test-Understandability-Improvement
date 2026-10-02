package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test12 extends TokenQueue_ESTest_scaffolding {

    private static final String EMPTY_IDENTIFIER = "";

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        String escapedIdentifier = TokenQueue.escapeCssIdentifier(EMPTY_IDENTIFIER);

        assertEquals(EMPTY_IDENTIFIER, escapedIdentifier);
    }
}
