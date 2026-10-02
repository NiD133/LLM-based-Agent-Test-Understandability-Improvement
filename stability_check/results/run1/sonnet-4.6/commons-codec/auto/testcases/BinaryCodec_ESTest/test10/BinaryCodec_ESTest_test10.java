package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test10 extends BinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BinaryCodec binaryCodec0 = new BinaryCodec();

        // 8-byte ASCII input representing the binary string "10000000":
        // byte[0] = ASCII '1' (49), bytes[1..7] = 0 (treated as '0')
        byte[] byteArray0 = new byte[8];
        byteArray0[0] = (byte) 49; // ASCII '1'

        // Decoding "10000000" yields 0x80 = -128 (only the most-significant bit is set)
        byte[] byteArray1 = binaryCodec0.decode(byteArray0);
        assertArrayEquals(new byte[] { (byte) (-128) }, byteArray1);
    }
}
