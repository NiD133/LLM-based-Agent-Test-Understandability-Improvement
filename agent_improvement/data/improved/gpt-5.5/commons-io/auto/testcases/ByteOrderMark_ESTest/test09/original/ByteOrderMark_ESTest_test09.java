package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test09 extends ByteOrderMark_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        ByteOrderMark byteOrderMark0 = ByteOrderMark.UTF_16BE;
        ByteOrderMark byteOrderMark1 = ByteOrderMark.UTF_32LE;
        boolean boolean0 = byteOrderMark0.equals(byteOrderMark1);
        assertFalse(boolean0);
    }
}
