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
     * Verifies that writing the value zero as an 8-byte little-endian sequence
     * produces exactly 8 null bytes in the output stream.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        long value = 0L;
        int byteCount = 8;

        ByteUtils.toLittleEndian((OutputStream) outputStream, value, byteCount);

        // Zero encoded in little-endian is all null bytes; toString() converts them to null characters
        String expectedNullBytes = new String(new byte[byteCount]);
        assertEquals(expectedNullBytes, outputStream.toString());
        assertEquals(byteCount, outputStream.size());
    }
}
