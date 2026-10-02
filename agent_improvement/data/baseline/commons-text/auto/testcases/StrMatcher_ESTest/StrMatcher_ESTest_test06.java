package org.apache.commons.text;

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
        char[] charArray0 = new char[0];
        StrMatcher strMatcher0 = StrMatcher.charSetMatcher(charArray0);
        assertNotNull(strMatcher0);
    }
}
