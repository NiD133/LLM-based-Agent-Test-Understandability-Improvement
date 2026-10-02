package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test16 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that cloning a tokenizer produces a distinct object instance
     * rather than returning the original tokenizer.
     */
    @Test(timeout = 4000)
    public void cloneReturnsDistinctInstance() throws Throwable {
        StringTokenizer originalTokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");

        Object clonedTokenizer = originalTokenizer.clone();

        assertNotSame(clonedTokenizer, originalTokenizer);
    }
}
