package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test34 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#abbreviate(String, int, int, String)} returns the
     * empty string unchanged when the input is empty, regardless of the lower/upper
     * limits or the append text.
     */
    @Test(timeout = 4000)
    public void abbreviateEmptyStringReturnsEmptyString() throws Throwable {
        String emptyInput = "";
        int lowerLimit = 0;
        int upperLimit = 1308;
        String appendToEnd = "";

        String result = WordUtils.abbreviate(emptyInput, lowerLimit, upperLimit, appendToEnd);

        assertEquals("", result);
    }
}
