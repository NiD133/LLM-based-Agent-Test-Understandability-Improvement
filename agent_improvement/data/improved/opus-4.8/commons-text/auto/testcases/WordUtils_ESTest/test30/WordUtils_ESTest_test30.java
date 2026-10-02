package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test30 extends WordUtils_ESTest_scaffolding {

    /**
     * When the string is abbreviated at the first space, {@code abbreviate} keeps
     * the text up to that space and appends the supplied suffix.
     *
     * <p>For "Y oD/'" with lower=0 and upper=1, the first space falls at index 1,
     * so only "Y" is kept before the suffix is appended.</p>
     */
    @Test(timeout = 4000)
    public void abbreviateAtFirstSpaceAppendsSuffix() throws Throwable {
        String input = "Y oD/'";
        int lowerBound = 0;
        int upperBound = 1;
        String suffix = "Upper value cannot be less than -1";

        String abbreviated = WordUtils.abbreviate(input, lowerBound, upperBound, suffix);

        assertEquals("YUpper value cannot be less than -1", abbreviated);
    }
}
