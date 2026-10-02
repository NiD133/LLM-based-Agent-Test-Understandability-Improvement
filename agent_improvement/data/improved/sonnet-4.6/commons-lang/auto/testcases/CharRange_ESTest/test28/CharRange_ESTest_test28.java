package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test28 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that a single-character, non-negated CharRange created via isIn('O', 'O')
     * reports correct start/end characters and is not negated.
     * Also ensures hashCode() completes without throwing.
     */
    @Test(timeout = 4000)
    public void test_hashCode_andProperties_forSingleCharRange() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        // hashCode() must not throw for a valid range
        singleCharRange.hashCode();

        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
        assertFalse(singleCharRange.isNegated());
    }
}
