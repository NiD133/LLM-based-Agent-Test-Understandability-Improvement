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
public class Hex_ESTest_test16 extends Hex_ESTest_scaffolding {

    /**
     * When dataLen=0, encodeHex writes nothing to the output array regardless of
     * the dataOffset or outOffset values — the encoding loop never executes,
     * so the output char array remains unmodified.
     */
    @Test(timeout = 4000)
    public void test_encodeHex_withZeroDataLen_doesNotWriteToOutputArray() throws Throwable {
        byte[] inputData = new byte[4];
        char[] outputChars = new char[2];

        int dataOffset = 422;
        int dataLen = 0;
        boolean toLowerCase = true;
        int outOffset = -435;

        Hex.encodeHex(inputData, dataOffset, dataLen, toLowerCase, outputChars, outOffset);

        assertEquals(2, outputChars.length);
    }
}
