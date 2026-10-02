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
        StrMatcher strMatcher0 = StrMatcher.charMatcher('M');
        char[] charArray0 = new char[6];
        charArray0[0] = 'M';
        int int0 = strMatcher0.isMatch(charArray0, 0, (-1), (-1996));
        assertEquals(1, int0);
    }
}
