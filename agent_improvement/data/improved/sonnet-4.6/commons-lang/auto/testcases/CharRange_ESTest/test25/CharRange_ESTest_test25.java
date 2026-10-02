package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test25 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that CharRange.isNot(ch) creates a negated single-character range
     * where both start and end equal the given character.
     */
    @Test(timeout = 4000)
    public void test_isNot_singleChar_createsNegatedRangeWithMatchingStartAndEnd() throws Throwable {
        CharRange negatedRange = CharRange.isNot('2');

        char endChar = negatedRange.getEnd();

        assertEquals('2', negatedRange.getStart());
        assertEquals('2', endChar);
        assertTrue(negatedRange.isNegated());
    }
}
