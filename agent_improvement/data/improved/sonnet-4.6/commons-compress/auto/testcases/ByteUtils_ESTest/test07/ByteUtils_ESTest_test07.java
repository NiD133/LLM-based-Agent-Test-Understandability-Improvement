package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test07 extends ByteUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        byte[] zeros = new byte[7];
        // Create a 2-byte window into the zero array starting at offset 2
        ByteArrayInputStream inputStream = new ByteArrayInputStream(zeros, 2, 2);
        DataInputStream dataInput = new DataInputStream(inputStream);

        // Read 1 byte as a little-endian long; the byte is 0x00 so the result is 0
        long result = ByteUtils.fromLittleEndian((DataInput) dataInput, 1);

        assertEquals(1, inputStream.available()); // one byte of the 2-byte window remains
        assertEquals(0L, result);
    }
}
