package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test06 extends CharRange_ESTest_scaffolding {

    /**
     * A CharRange over the single character 'O' should preserve its start and
     * end bounds and must not be considered equal to an unrelated plain Object.
     */
    @Test(timeout = 4000)
    public void singleCharRangeIsNotEqualToArbitraryObject() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        boolean equalsPlainObject = singleCharRange.equals(new Object());

        assertFalse("CharRange must not equal a non-CharRange object", equalsPlainObject);
        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
    }
}
