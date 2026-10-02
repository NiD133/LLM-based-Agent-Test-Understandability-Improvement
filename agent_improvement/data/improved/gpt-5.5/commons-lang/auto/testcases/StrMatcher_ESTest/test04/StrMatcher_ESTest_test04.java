package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test04 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        char[] zeroFilledBuffer = new char[6];
        int firstBufferPosition = (int) '\u0000';
        StrMatcher.TrimMatcher trimMatcher = new StrMatcher.TrimMatcher();

        int matchLength = trimMatcher.isMatch(zeroFilledBuffer, firstBufferPosition);

        assertEquals(1, matchLength);
    }
}
