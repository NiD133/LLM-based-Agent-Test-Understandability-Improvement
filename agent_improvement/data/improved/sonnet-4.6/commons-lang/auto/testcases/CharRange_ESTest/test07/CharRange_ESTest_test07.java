package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test07 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that a range from '2' to 'U' does not contain a single-character
     * range for '~', because '~' (ASCII 126) lies outside '2'-'U' (ASCII 50-85).
     */
    @Test(timeout = 4000)
    public void test07_rangeDoesNotContainCharOutsideBounds() throws Throwable {
        CharRange rangeDigit2ToUpperU = CharRange.isIn('2', 'U');
        CharRange rangeSingleTilde = CharRange.is('~');

        boolean rangeContainsTilde = rangeDigit2ToUpperU.contains(rangeSingleTilde);

        assertEquals('2', rangeDigit2ToUpperU.getStart());
        assertEquals('U', rangeDigit2ToUpperU.getEnd());
        assertEquals('~', rangeSingleTilde.getStart());
        assertEquals('~', rangeSingleTilde.getEnd());
        assertFalse(rangeSingleTilde.isNegated());
        assertFalse(rangeContainsTilde);
    }
}
