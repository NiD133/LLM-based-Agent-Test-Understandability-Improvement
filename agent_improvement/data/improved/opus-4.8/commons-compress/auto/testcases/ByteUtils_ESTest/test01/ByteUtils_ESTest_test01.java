package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test01 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that writing the value 0 as an 8-byte little-endian sequence
     * emits exactly eight zero (NUL, 0x00) bytes to the target stream.
     */
    @Test(timeout = 4000)
    public void toLittleEndian_writesEightZeroBytesForZeroValue() throws Throwable {
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        long valueToWrite = 0L;
        int numberOfBytes = 8;

        ByteUtils.toLittleEndian((OutputStream) target, valueToWrite, numberOfBytes);

        // Writing 0 across eight bytes yields eight 0x00 bytes, i.e. a string of
        // eight NUL characters (each zero byte decodes to U+0000).
        String expectedEightNulBytes = new String(new byte[8]);
        assertEquals(expectedEightNulBytes, target.toString());
        assertEquals(8, target.size());
    }
}
