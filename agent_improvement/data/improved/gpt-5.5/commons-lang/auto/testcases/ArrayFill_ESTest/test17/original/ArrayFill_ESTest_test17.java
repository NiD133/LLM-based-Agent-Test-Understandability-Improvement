package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Arrays;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test17 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        byte[] byteArray0 = new byte[1];
        byte[] byteArray1 = ArrayFill.clear(byteArray0);
        assertArrayEquals(new byte[] { (byte) 0 }, byteArray1);
    }
}
