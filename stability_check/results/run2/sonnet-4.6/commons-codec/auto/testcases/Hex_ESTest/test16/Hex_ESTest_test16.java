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
     * Verifies that encoding zero bytes leaves the output char array unmodified,
     * even when dataOffset and outOffset are out-of-range values.
     * The internal loop condition (i < dataOffset + dataLen) is immediately false
     * when dataLen=0, so no writes to the output array occur.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        byte[] inputData = new byte[4];
        char[] outputChars = new char[2];

        int dataOffset = 422;   // intentionally out-of-bounds for inputData
        int dataLen = 0;        // encode zero bytes — the loop body never executes
        boolean toLowerCase = true;
        int outOffset = -435;   // intentionally negative; safe because no bytes are written

        Hex.encodeHex(inputData, dataOffset, dataLen, toLowerCase, outputChars, outOffset);

        // output array is untouched since dataLen=0 caused the encoding loop to be skipped
        assertEquals(2, outputChars.length);
    }
}
