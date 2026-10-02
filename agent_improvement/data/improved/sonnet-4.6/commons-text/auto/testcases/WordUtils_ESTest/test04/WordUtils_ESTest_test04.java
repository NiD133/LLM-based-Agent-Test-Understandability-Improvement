package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test04 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_wrap_withNegativeWrapLength_returnsInputUnchanged() throws Throwable {
        // A very negative wrapLength is clamped to 1 internally; a string with no
        // spaces cannot be broken, so wrap() must return the original string as-is.
        String inputWithNoSpaces = ".*\b";
        int negativeWrapLength = -1995;

        String result = WordUtils.wrap(inputWithNoSpaces, negativeWrapLength);

        assertEquals(inputWithNoSpaces, result);
    }
}
