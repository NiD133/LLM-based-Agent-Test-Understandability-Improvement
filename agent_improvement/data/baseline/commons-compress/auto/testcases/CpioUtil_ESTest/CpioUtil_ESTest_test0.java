package org.apache.commons.compress.archivers.cpio;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CpioUtil_ESTest_test0 extends CpioUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        byte[] byteArray0 = CpioUtil.long2byteArray((-1L), 8, true);
        assertArrayEquals(new byte[] { (byte) (-1), (byte) (-1), (byte) (-1), (byte) (-1), (byte) (-1), (byte) (-1), (byte) (-1), (byte) (-1) }, byteArray0);
    }
}
