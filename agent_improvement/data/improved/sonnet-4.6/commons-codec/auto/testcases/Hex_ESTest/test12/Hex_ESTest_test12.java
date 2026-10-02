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
public class Hex_ESTest_test12 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_encodeSingleZeroByte_returnsHexBytes() throws Throwable {
        Hex hexEncoder = new Hex();
        byte[] singleZeroByte = new byte[1];
        byte[] hexEncodedBytes = hexEncoder.encode(singleZeroByte);
        //  // Unstable assertion: assertEquals(2, hexEncodedBytes.length);
        //  // Unstable assertion: assertArrayEquals(new byte[] {(byte)51, (byte)51}, hexEncodedBytes);
    }
}
