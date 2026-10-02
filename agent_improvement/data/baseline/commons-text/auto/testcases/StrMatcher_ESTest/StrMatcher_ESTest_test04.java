package org.apache.commons.text;

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
        StrMatcher strMatcher0 = StrMatcher.charSetMatcher("");
        assertNotNull(strMatcher0);
    }
}
