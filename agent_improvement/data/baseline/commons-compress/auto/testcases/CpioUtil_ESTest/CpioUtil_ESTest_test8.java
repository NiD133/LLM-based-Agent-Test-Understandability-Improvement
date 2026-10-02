package org.apache.commons.compress.archivers.cpio;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CpioUtil_ESTest_test8 extends CpioUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        CpioUtil cpioUtil0 = new CpioUtil();
    }
}
