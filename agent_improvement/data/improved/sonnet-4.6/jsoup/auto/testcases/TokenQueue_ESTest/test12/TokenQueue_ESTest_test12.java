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

    // escapeCssIdentifier short-circuits and returns the empty string unchanged
    // when given an empty input (no characters need escaping).
    @Test(timeout = 4000)
    public void test_escapeCssIdentifier_emptyInput_returnsEmptyString() throws Throwable {
        String result = TokenQueue.escapeCssIdentifier("");
        assertEquals("", result);
    }
}
