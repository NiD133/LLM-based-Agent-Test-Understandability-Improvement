package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test15 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        String[] stringArray0 = new String[2];
        String string0 = CharSetUtils.squeeze("Minimum abbreviation width with offset is %d", stringArray0);
        assertEquals("Minimum abbreviation width with offset is %d", string0);
    }
}
