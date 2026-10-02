package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test41 extends WordUtils_ESTest_scaffolding {

    /**
     * A string consisting solely of whitespace has no word characters to
     * uncapitalize, so {@link WordUtils#uncapitalize(String)} returns it unchanged.
     */
    @Test(timeout = 4000)
    public void uncapitalizeSingleSpaceReturnsSameSpace() throws Throwable {
        String result = WordUtils.uncapitalize(" ");

        assertEquals(" ", result);
    }
}
