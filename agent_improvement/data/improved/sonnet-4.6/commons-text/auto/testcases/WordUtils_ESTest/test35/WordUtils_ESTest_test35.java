package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test35 extends WordUtils_ESTest_scaffolding {

    /**
     * WordUtils.abbreviate requires upper >= -1. An upper value below -1 (e.g. -1881)
     * must throw IllegalArgumentException with the message "upper value cannot be less than -1".
     */
    @Test(timeout = 4000)
    public void test35_abbreviateThrowsWhenUpperIsBelowNegativeOne() throws Throwable {
        int lowerLimit = 0;
        int invalidUpperLimit = -1881; // any value less than -1 is illegal
        String appendToEnd = "?AnDuL6yPz+";

        try {
            WordUtils.abbreviate("LZzA7+<X<Kkh0y", lowerLimit, invalidUpperLimit, appendToEnd);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
