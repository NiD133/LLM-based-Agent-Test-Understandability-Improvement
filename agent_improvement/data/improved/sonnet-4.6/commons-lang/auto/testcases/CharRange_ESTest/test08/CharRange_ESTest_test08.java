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
     * Verifies that a single-character range ['y','y'] does not contain
     * a disjoint single-character range ['D','D'], and that the properties
     * of each range are consistent with their construction.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Range covering only 'y'
        CharRange rangeY = CharRange.isIn('y', 'y');
        assertEquals('y', rangeY.getStart());
        assertEquals('y', rangeY.getEnd());

        // Range covering only 'D'
        CharRange rangeD = CharRange.is('D');
        assertEquals('D', rangeD.getStart());
        assertEquals('D', rangeD.getEnd());
        assertFalse(rangeD.isNegated());

        // ['y','y'] does not contain ['D','D'] because 'D' (68) < 'y' (121)
        assertFalse(rangeY.contains(rangeD));
    }
}
