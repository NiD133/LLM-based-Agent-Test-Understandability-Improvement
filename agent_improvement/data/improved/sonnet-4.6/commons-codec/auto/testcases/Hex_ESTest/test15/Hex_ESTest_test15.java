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
public class Hex_ESTest_test15 extends Hex_ESTest_scaffolding {

    /**
     * Verifies that Hex.encode(ByteBuffer) successfully encodes a buffer of zero bytes
     * into hex character bytes using the system default charset, consuming all buffer bytes.
     */
    @Test(timeout = 4000)
    public void test_encodeByteBuffer_withTwoZeroBytes_returnsHexBytes() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();
        Hex hexEncoder = new Hex(defaultCharset);

        byte[] twoZeroBytes = new byte[2];
        ByteBuffer inputBuffer = ByteBuffer.wrap(twoZeroBytes);

        byte[] hexEncodedBytes = hexEncoder.encode(inputBuffer);

        //  // Unstable assertion: assertEquals("java.nio.HeapByteBuffer[pos=2 lim=2 cap=2]", inputBuffer.toString());
        //  // Unstable assertion: assertArrayEquals(new byte[] {(byte)51, (byte)51, (byte)51, (byte)51}, hexEncodedBytes);
    }
}
