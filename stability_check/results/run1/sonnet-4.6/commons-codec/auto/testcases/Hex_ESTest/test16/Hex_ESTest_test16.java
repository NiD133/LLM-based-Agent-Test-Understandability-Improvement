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
     * Verifies that encoding zero bytes (dataLen=0) is a no-op regardless of
     * the other offset arguments. When dataLen is 0 the internal loop body
     * never executes, so neither the out-of-bounds source offset (422) nor the
     * negative output offset (-435) cause an exception, and the output array
     * is left completely unchanged.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        byte[] sourceBytes = new byte[4];
        char[] outputChars = new char[2];

        int dataOffset   = 422;  // out-of-bounds, but harmless when dataLen == 0
        int dataLen      = 0;    // encode nothing — the loop is skipped entirely
        boolean useLower = true;
        int outOffset    = -435; // negative, but harmless when dataLen == 0

        Hex.encodeHex(sourceBytes, dataOffset, dataLen, useLower, outputChars, outOffset);

        // The output array must be untouched because no bytes were encoded
        assertEquals(2, outputChars.length);
    }
}
