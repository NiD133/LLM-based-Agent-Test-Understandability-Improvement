package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test06 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        String[] stringArray0 = new String[1];
        String string0 = CharSetUtils.keep((String) null, stringArray0);
        assertNull(string0);
    }
}
