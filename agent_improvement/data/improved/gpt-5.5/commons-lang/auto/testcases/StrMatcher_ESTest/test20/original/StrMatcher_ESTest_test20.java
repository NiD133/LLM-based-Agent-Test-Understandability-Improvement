package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test20 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        StrMatcher strMatcher0 = StrMatcher.trimMatcher();
        char[] charArray0 = new char[5];
        charArray0[0] = 'D';
        int int0 = strMatcher0.isMatch(charArray0, 0, 0, 0);
        assertEquals(0, int0);
    }
}
