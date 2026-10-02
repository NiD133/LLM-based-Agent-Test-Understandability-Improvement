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
     * {@link WordUtils#abbreviate(String, int, int, String)} requires the upper
     * limit to be at least -1 (where -1 means "no limit"). Passing an upper
     * limit below -1 must fail the {@code upper >= -1} validation and raise an
     * {@link IllegalArgumentException} from Apache Commons Lang's Validate.
     */
    @Test(timeout = 4000)
    public void abbreviateRejectsUpperLimitBelowMinusOne() throws Throwable {
        String text = "LZzA7+<X<Kkh0y";
        int lowerLimit = 0;
        int invalidUpperLimit = -1881; // below the smallest allowed value of -1
        String appendToEnd = "?AnDuL6yPz+";

        try {
            WordUtils.abbreviate(text, lowerLimit, invalidUpperLimit, appendToEnd);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // "upper value cannot be less than -1"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
