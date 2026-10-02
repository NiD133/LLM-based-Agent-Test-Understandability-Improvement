package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test27 extends CharRange_ESTest_scaffolding {

    // Verifies that isIn('O','O') creates a non-negated single-character range
    // where both start and end are 'O'.
    @Test(timeout = 4000)
    public void test27() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        char start = singleCharRange.getStart();

        assertFalse("Range created with isIn should not be negated", singleCharRange.isNegated());
        assertEquals("Start character should be 'O'", 'O', start);
        assertEquals("End character should be 'O'", 'O', singleCharRange.getEnd());
    }
}
