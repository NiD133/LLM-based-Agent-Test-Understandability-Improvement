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
public class Hex_ESTest_test06 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        char[] hexCharacters = new char[6];
        byte[] outputBuffer = new byte[7];
        int impossibleOutputOffset = 1589;

        try {
            Hex.decodeHex(hexCharacters, outputBuffer, impossibleOutputOffset);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Output array is not large enough to accommodate decoded data.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
