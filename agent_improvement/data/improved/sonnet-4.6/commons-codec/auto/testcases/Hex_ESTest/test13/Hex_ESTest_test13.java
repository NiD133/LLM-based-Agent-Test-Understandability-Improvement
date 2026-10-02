package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test13 extends Hex_ESTest_scaffolding {

    // encodeHex(data, dataOffset, dataLen, toLowerCase) throws ArrayIndexOutOfBoundsException
    // when dataLen exceeds the number of bytes available in the array from dataOffset.
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        byte[] fourByteArray = new byte[4];
        int startOffset = 0;
        int requestedLength = 678; // far exceeds the 4-byte array size

        try {
            Hex.encodeHex(fourByteArray, startOffset, requestedLength, false);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // 4
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
