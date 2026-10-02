package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test07 extends CharRange_ESTest_scaffolding {

    /**
     * The range '2'..'U' does not contain the single-character range '~',
     * because '~' falls outside the ['2', 'U'] interval.
     */
    @Test(timeout = 4000)
    public void testRangeDoesNotContainCharacterOutsideIt() throws Throwable {
        CharRange rangeTwoToU = CharRange.isIn('2', 'U');
        CharRange rangeTilde = CharRange.is('~');

        boolean tildeContained = rangeTwoToU.contains(rangeTilde);
        assertFalse(tildeContained);

        // The two ranges keep their original boundaries and are not negated.
        assertEquals('2', rangeTwoToU.getStart());
        assertEquals('U', rangeTwoToU.getEnd());
        assertEquals('~', rangeTilde.getStart());
        assertEquals('~', rangeTilde.getEnd());
        assertFalse(rangeTilde.isNegated());
    }
}
