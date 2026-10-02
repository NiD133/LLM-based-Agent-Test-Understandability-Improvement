package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test06 extends Hex_ESTest_scaffolding {

    /**
     * decodeHex(char[], out, outOffset) needs (data.length / 2) bytes of room in
     * {@code out}, starting at {@code outOffset}. Here the 6 hex chars require 3
     * output bytes, but the offset (1589) starts far beyond the end of the
     * 7-byte output array, leaving no space. The method must reject this with a
     * DecoderException complaining that the output array is too small.
     */
    @Test(timeout = 4000)
    public void decodeHexThrowsWhenOutputOffsetLeavesNoRoom() throws Throwable {
        char[] hexChars = new char[6];
        byte[] decodedOutput = new byte[7];
        int outOffsetBeyondArray = 1589;

        try {
            Hex.decodeHex(hexChars, decodedOutput, outOffsetBeyondArray);
            fail("Expected a DecoderException because the output array cannot accommodate the decoded data");
        } catch (Exception e) {
            // Output array is not large enough to accommodate decoded data.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
