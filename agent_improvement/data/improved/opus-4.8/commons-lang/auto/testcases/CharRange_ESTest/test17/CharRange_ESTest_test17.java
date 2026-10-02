package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test17 extends CharRange_ESTest_scaffolding {

    /**
     * A range that spans the single character 'O' should report that it
     * contains itself, and should expose 'O' as both its start and end.
     */
    @Test(timeout = 4000)
    public void singleCharRangeContainsItself() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        boolean containsItself = singleCharRange.contains(singleCharRange);

        assertTrue("A range should always contain itself", containsItself);
        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
    }
}
