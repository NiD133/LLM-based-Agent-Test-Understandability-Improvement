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

    /**
     * Verifies that abbreviate() throws IllegalArgumentException when the upper bound
     * is less than the lower bound (upper=1693 < lower=3451).
     */
    @Test(timeout = 4000)
    public void test32() throws Throwable {
        String input = "M%VpyY9";
        int lowerBound = 3451;
        int upperBound = 1693;
        String appendToEnd = "M%VpyY9";

        try {
            WordUtils.abbreviate(input, lowerBound, upperBound, appendToEnd);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
