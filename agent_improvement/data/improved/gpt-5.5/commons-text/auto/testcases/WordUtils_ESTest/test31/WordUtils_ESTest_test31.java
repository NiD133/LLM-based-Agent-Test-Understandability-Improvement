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

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        final String originalText = "TC& wHYFqV 45";
        final int lowerLimitPastEnd = 911;
        final int upperLimitPastEnd = 911;
        final String appendWhenAbbreviated = "";

        final String abbreviated = WordUtils.abbreviate(originalText, lowerLimitPastEnd, upperLimitPastEnd, appendWhenAbbreviated);

        assertEquals(originalText, abbreviated);
    }
}
