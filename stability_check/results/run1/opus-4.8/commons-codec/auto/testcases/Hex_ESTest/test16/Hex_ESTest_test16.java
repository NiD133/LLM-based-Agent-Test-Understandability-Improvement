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
     * When {@code dataLen} is zero, {@link Hex#encodeHex} encodes no bytes, so the
     * loop never runs and the otherwise out-of-range {@code dataOffset} and
     * {@code outOffset} are never dereferenced. The call completes without touching
     * the output array, which therefore keeps its original length.
     */
    @Test(timeout = 4000)
    public void encodeHexWithZeroLengthLeavesOutputUntouched() throws Throwable {
        byte[] source = new byte[4];
        char[] output = new char[2];

        int dataOffset = 422;
        int dataLen = 0;
        boolean toLowerCase = true;
        int outOffset = -435;

        Hex.encodeHex(source, dataOffset, dataLen, toLowerCase, output, outOffset);

        assertEquals(2, output.length);
    }
}
