package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test09 extends BinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        byte[] byteArray0 = BinaryCodec.fromAscii((char[]) null);
        assertArrayEquals(new byte[] {}, byteArray0);
    }
}
