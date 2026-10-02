package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test11 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that swapping the case of an empty String yields an empty String,
     * since there are no characters whose case can be changed.
     */
    @Test(timeout = 4000)
    public void swapCaseOfEmptyStringReturnsEmptyString() throws Throwable {
        String result = WordUtils.swapCase("");

        assertEquals("", result);
    }
}
