package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test33 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        // Input has no spaces, so no word boundary is found to abbreviate at.
        // With upper = -1 (no upper limit), the entire string is used and no
        // truncation occurs, so appendToEnd is never appended.
        String input = "org.apache.commons.text.WordUtils";

        String result = WordUtils.abbreviate(input, 0, -1, input);

        assertEquals(input, result);
    }
}
