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
        // Undeclared exception!
        try {
            WordUtils.abbreviate("M%VpyY9", 3451, 1693, "M%VpyY9");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // upper value is less than lower value
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
