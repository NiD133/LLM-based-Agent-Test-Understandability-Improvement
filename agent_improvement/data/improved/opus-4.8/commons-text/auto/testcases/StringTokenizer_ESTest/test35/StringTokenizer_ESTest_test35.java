package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test35 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Calling previousToken() before any token has been read should return null,
     * because the tokenizer's cursor starts before the first token.
     */
    @Test(timeout = 4000)
    public void previousTokenBeforeAnyReadReturnsNull() throws Throwable {
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance("%*,d O%");
        csvTokenizer.setIgnoredChar('*');

        String tokenBeforeStart = csvTokenizer.previousToken();

        assertNull(tokenBeforeStart);
    }
}
