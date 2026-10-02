package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test01 extends CharRange_ESTest_scaffolding {

    /**
     * Two separately created single-character ranges for the same character
     * should be equal, and the range should expose that character as both its
     * start and end while remaining non-negated.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        CharRange firstRangeForY = CharRange.is('y');
        CharRange secondRangeForY = CharRange.is('y');

        boolean rangesAreEqual = secondRangeForY.equals(firstRangeForY);

        assertEquals('y', secondRangeForY.getEnd());
        assertEquals('y', secondRangeForY.getStart());
        assertFalse(secondRangeForY.isNegated());
        assertTrue(rangesAreEqual);
    }
}
