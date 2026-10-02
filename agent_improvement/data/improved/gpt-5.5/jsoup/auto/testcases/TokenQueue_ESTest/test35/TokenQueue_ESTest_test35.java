package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test35 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test35() throws Throwable {
        String identifierWithBackspace = "p\bmoD0M";
        String expectedEscapedIdentifier = "p\\8 moD0M";

        String escapedIdentifier = TokenQueue.escapeCssIdentifier(identifierWithBackspace);

        assertEquals(expectedEscapedIdentifier, escapedIdentifier);
    }
}
