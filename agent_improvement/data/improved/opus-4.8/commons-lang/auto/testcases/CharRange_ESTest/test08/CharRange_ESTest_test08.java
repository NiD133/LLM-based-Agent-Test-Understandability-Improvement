package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test08 extends CharRange_ESTest_scaffolding {

    /**
     * A single-character range ('y') does not contain the unrelated
     * single-character range ('D'), since 'D' lies outside ['y', 'y'].
     */
    @Test(timeout = 4000)
    public void testSingleCharRangeDoesNotContainDisjointRange() throws Throwable {
        CharRange rangeOfY = CharRange.isIn('y', 'y');
        CharRange rangeOfD = CharRange.is('D');

        boolean yContainsD = rangeOfY.contains(rangeOfD);
        assertFalse(yContainsD);

        // The ['y', 'y'] range spans exactly the single character 'y'.
        assertEquals('y', rangeOfY.getStart());
        assertEquals('y', rangeOfY.getEnd());

        // The 'D' range spans exactly the single character 'D' and is not negated.
        assertEquals('D', rangeOfD.getStart());
        assertEquals('D', rangeOfD.getEnd());
        assertFalse(rangeOfD.isNegated());
    }
}
