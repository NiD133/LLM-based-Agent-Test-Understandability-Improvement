package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test01 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        String[] stringArray0 = new String[4];
        stringArray0[1] = "ZS[4!;6>G|3UPaJfj";
        String string0 = CharSetUtils.squeeze("Minimum abbreviation width with offset is %d", stringArray0);
        assertEquals("Minimum abbreviation width with ofset is %d", string0);
    }
}
