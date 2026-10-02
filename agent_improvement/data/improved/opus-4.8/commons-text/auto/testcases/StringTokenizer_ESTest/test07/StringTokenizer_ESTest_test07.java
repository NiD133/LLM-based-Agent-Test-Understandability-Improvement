package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test07 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Calling previousToken() on a freshly created tokenizer (before any token
     * has been read) should return null, because there is no previous token to
     * step back to.
     */
    @Test(timeout = 4000)
    public void previousTokenBeforeAnyIterationReturnsNull() throws Throwable {
        char[] input = new char[9];
        input[0] = ')';
        input[1] = ')';
        input[4] = ')';
        char delimiter = '(';
        char quoteChar = ')';

        StringTokenizer tokenizer = new StringTokenizer(input, delimiter, quoteChar);

        String previousToken = tokenizer.previousToken();

        assertNull(previousToken);
    }
}
