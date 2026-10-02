package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test19 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        StrMatcher strMatcher0 = StrMatcher.noneMatcher();
        char[] charArray0 = new char[1];
        int int0 = strMatcher0.isMatch(charArray0, (int) '\u0000', (int) '\u0000', (int) '\u0000');
        assertEquals(0, int0);
    }
}
