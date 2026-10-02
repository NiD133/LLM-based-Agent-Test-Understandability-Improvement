package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test27 extends CharRange_ESTest_scaffolding {

    /**
     * A range built from a single character repeated as both endpoints
     * ('O' to 'O') should be a non-negated range whose start and end are
     * both that character.
     */
    @Test(timeout = 4000)
    public void testSingleCharacterRangeHasMatchingStartAndEnd() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
        assertFalse(singleCharRange.isNegated());
    }
}
