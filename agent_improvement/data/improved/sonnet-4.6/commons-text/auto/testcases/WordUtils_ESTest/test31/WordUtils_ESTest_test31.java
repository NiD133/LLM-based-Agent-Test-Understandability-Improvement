package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test31 extends WordUtils_ESTest_scaffolding {

    // When both lower and upper limits exceed the string length, the full string is returned unchanged.
    @Test(timeout = 4000)
    public void testAbbreviateReturnsFullStringWhenLimitsExceedStringLength() throws Throwable {
        String input = "TC& wHYFqV 45";
        String result = WordUtils.abbreviate(input, 911, 911, "");
        assertEquals("TC& wHYFqV 45", result);
    }
}
