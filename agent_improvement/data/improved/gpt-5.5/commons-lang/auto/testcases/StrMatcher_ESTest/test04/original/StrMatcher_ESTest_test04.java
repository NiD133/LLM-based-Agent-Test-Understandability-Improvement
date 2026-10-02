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
        char[] charArray0 = new char[6];
        StrMatcher.TrimMatcher strMatcher_TrimMatcher0 = new StrMatcher.TrimMatcher();
        int int0 = strMatcher_TrimMatcher0.isMatch(charArray0, (int) '\u0000');
        assertEquals(1, int0);
    }
}
