package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test04 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range and a non-negated multi-character range
     * are not equal, even when they share an endpoint. This test also confirms
     * the basic properties (start, end, negation) of each range.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Negated range covering everything except '2'.
        CharRange negatedSingle = CharRange.isNot('2');
        // Plain inclusive range from '2' to 'U'.
        CharRange inclusiveRange = CharRange.isIn('2', 'U');

        boolean areEqual = inclusiveRange.equals(negatedSingle);

        // The two ranges differ in span and negation, so they are not equal.
        assertFalse(areEqual);

        // Properties of the negated single-character range.
        assertTrue(negatedSingle.isNegated());
        assertEquals('2', negatedSingle.getStart());
        assertEquals('2', negatedSingle.getEnd());

        // Properties of the inclusive range.
        assertEquals('2', inclusiveRange.getStart());
        assertEquals('U', inclusiveRange.getEnd());
    }
}
