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
     * When {@code encodeHex} is asked to encode zero bytes (dataLen == 0), it
     * performs no work: the encoding loop never runs, so neither the wildly
     * out-of-range dataOffset nor the negative outOffset is ever dereferenced,
     * and the output buffer is left untouched.
     */
    @Test(timeout = 4000)
    public void encodingZeroBytesLeavesOutputBufferUntouched() throws Throwable {
        byte[] sourceBytes = new byte[4];
        char[] outputBuffer = new char[2];

        int dataOffset = 422;
        int dataLen = 0;
        boolean toLowerCase = true;
        int outOffset = -435;

        Hex.encodeHex(sourceBytes, dataOffset, dataLen, toLowerCase, outputBuffer, outOffset);

        assertEquals(2, outputBuffer.length);
    }
}
