package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test06 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        final String pattern = "";
        final char[] buffer = new char[1];
        final int matchPosition = -616;
        final int bufferStart = 4;
        final int bufferEnd = -616;

        final StrMatcher.StringMatcher matcher = new StrMatcher.StringMatcher(pattern);
        final int matchLength = matcher.isMatch(buffer, matchPosition, bufferStart, bufferEnd);

        assertEquals(0, matchLength);
    }
}
