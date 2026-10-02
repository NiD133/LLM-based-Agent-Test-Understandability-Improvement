package org.apache.commons.text;

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
        StrMatcher strMatcher0 = StrMatcher.stringMatcher("W");
        char[] charArray0 = new char[3];
        charArray0[1] = 'W';
        int int0 = strMatcher0.isMatch(charArray0, 1, (int) 'W', (int) 'W');
        assertEquals(1, int0);
    }
}
