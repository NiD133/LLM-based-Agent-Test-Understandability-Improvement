package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test16 extends Hex_ESTest_scaffolding {

    /**
     * When the requested data length is zero, {@link Hex#encodeHex(byte[], int, int, boolean, char[], int)}
     * encodes nothing: its internal loop never runs, so the (otherwise out-of-range) source and
     * destination offsets are never dereferenced and the output buffer is left untouched.
     */
    @Test(timeout = 4000)
    public void encodeZeroBytesWritesNothingToOutput() throws Throwable {
        byte[] source = new byte[4];
        char[] output = new char[2];

        int dataOffset = 422;          // out of bounds, but never read because dataLen is 0
        int dataLength = 0;            // nothing to encode
        boolean toLowerCase = true;
        int outOffset = -435;          // out of bounds, but never written for the same reason

        Hex.encodeHex(source, dataOffset, dataLength, toLowerCase, output, outOffset);

        // Output buffer is returned unchanged: same length, no characters written.
        assertEquals(2, output.length);
    }
}
