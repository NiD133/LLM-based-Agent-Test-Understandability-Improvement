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
     * A single-character range created with {@code isIn('O', 'O')} should span
     * exactly the character 'O' and should not be negated.
     */
    @Test(timeout = 4000)
    public void testSingleCharacterRangeExposesBoundsAndIsNotNegated() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
        assertFalse(singleCharRange.isNegated());
    }
}
