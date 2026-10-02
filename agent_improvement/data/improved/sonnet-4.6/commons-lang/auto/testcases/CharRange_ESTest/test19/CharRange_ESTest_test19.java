package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test19 extends CharRange_ESTest_scaffolding {

    /**
     * A single-character range created with isIn('O', 'O') should have both
     * start and end set to 'O', and must report that it contains 'O'.
     */
    @Test(timeout = 4000)
    public void test_singleCharRange_containsExactChar() throws Throwable {
        CharRange charRange0 = CharRange.isIn('O', 'O');

        assertEquals('O', charRange0.getStart());
        assertEquals('O', charRange0.getEnd());

        boolean containsO = charRange0.contains('O');
        assertTrue(containsO);
    }
}
