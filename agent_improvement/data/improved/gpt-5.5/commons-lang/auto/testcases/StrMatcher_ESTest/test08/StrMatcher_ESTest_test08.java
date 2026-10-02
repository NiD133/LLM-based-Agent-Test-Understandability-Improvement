package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test08 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        StrMatcher matcher = StrMatcher.charMatcher('M');
        char[] buffer = new char[6];
        buffer[0] = 'M';

        // CharMatcher only checks buffer[pos], so these unusual bounds preserve the original regression case.
        int matchLength = matcher.isMatch(buffer, 0, (-1), (-1996));

        assertEquals(1, matchLength);
    }
}
