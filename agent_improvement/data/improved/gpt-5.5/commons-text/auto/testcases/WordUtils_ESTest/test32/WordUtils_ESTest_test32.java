package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test32 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test32() throws Throwable {
        final String input = "M%VpyY9";
        final int lowerLimit = 3451;
        final int upperLimitBelowLowerLimit = 1693;
        final String appendToEnd = "M%VpyY9";

        try {
            WordUtils.abbreviate(input, lowerLimit, upperLimitBelowLowerLimit, appendToEnd);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
