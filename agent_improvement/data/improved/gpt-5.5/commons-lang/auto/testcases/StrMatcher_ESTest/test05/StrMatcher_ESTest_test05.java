package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test05 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final char[] buffer = new char[4];
        buffer[1] = 'T';

        final StrMatcher.StringMatcher matcher = new StrMatcher.StringMatcher("The type must not be null");

        final int matchLength = matcher.isMatch(buffer, 1, 6, 365);

        assertEquals(0, matchLength);
    }
}
