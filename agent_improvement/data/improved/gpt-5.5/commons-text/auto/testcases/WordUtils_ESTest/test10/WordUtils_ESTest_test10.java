package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test10 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void swapCaseInvertsLettersAndLeavesPunctuationUnchanged() throws Throwable {
        final String input = "-l]U*[b,I?0";
        final String expected = "-L]u*[B,i?0";

        final String actual = WordUtils.swapCase(input);

        assertEquals(expected, actual);
    }
}
