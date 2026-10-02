package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test33 extends WordUtils_ESTest_scaffolding {

    /**
     * When the upper bound is -1 (meaning "no upper limit"), abbreviate keeps the
     * whole string up to its full length. Since the text "org.apache.commons.text.WordUtils"
     * contains no spaces, no truncation point is found, so the original string is returned
     * unchanged and the appendToEnd suffix is never added.
     */
    @Test(timeout = 4000)
    public void abbreviateWithNoUpperLimitReturnsOriginalWhenNoSpacePresent() throws Throwable {
        String text = "org.apache.commons.text.WordUtils";
        int lowerBound = 0;
        int noUpperLimit = -1;
        String appendToEnd = "org.apache.commons.text.WordUtils";

        String result = WordUtils.abbreviate(text, lowerBound, noUpperLimit, appendToEnd);

        assertEquals("org.apache.commons.text.WordUtils", result);
    }
}
