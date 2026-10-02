package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test09 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A CSV-configured tokenizer parsing input that contains no delimiters
     * should treat the whole string as a single token. After reading that token
     * forward, stepping back with previousToken() should return the same value.
     */
    @Test(timeout = 4000)
    public void nextThenPreviousReturnsSameSingleToken() throws Throwable {
        String singleField = "%*dR|";
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance(singleField);

        String forwardToken = tokenizer.nextToken();
        assertEquals("cursor should advance past the only token", 1, tokenizer.nextIndex());

        String backwardToken = tokenizer.previousToken();
        assertEquals(singleField, backwardToken);
    }
}
