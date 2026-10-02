package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test27 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isIn_singleChar_returnsNonNegatedRangeWithMatchingStartAndEnd() throws Throwable {
        // A range created with the same start and end character should be a single-character, non-negated range
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        char start = singleCharRange.getStart();

        assertFalse(singleCharRange.isNegated());
        assertEquals('O', start);
        assertEquals('O', singleCharRange.getEnd());
    }
}
