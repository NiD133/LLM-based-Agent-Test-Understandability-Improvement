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

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Charset charset0 = Charset.defaultCharset();
        Hex hex0 = new Hex(charset0);
        byte[] byteArray0 = new byte[2];
        ByteBuffer byteBuffer0 = ByteBuffer.wrap(byteArray0);
        byte[] byteArray1 = hex0.encode(byteBuffer0);
        //  // Unstable assertion: assertEquals("java.nio.HeapByteBuffer[pos=2 lim=2 cap=2]", byteBuffer0.toString());
        //  // Unstable assertion: assertArrayEquals(new byte[] {(byte)51, (byte)51, (byte)51, (byte)51}, byteArray1);
    }
}
