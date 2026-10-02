package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test22 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        StrMatcher.StringMatcher strMatcher_StringMatcher0 = new StrMatcher.StringMatcher("^");
        char[] charArray0 = new char[4];
        int int0 = strMatcher_StringMatcher0.isMatch(charArray0, 2860, 0, 2860);
        assertEquals(0, int0);
    }
}
