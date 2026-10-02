package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test08 extends BinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        BinaryCodec binaryCodec0 = new BinaryCodec();
        byte[] byteArray0 = binaryCodec0.toByteArray("00000001110010111111111110111000101100100000000011111111");
        assertArrayEquals(new byte[] { (byte) (-1), (byte) 0, (byte) (-78), (byte) (-72), (byte) (-1), (byte) (-53), (byte) 1 }, byteArray0);
    }
}
