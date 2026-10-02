package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test31 extends WordUtils_ESTest_scaffolding {

    /**
     * When both the lower and upper limits are larger than the input length,
     * there is nothing to abbreviate, so the original string is returned
     * unchanged (and the suffix is not appended).
     */
    @Test(timeout = 4000)
    public void abbreviateWithLimitsLargerThanInputReturnsInputUnchanged() throws Throwable {
        String input = "TC& wHYFqV 45";
        int lowerLimitBeyondLength = 911;
        int upperLimitBeyondLength = 911;
        String suffix = "";

        String result = WordUtils.abbreviate(input, lowerLimitBeyondLength, upperLimitBeyondLength, suffix);

        assertEquals("TC& wHYFqV 45", result);
    }
}
