package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test07 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that the UTF-32LE byte order mark exposes its little-endian
     * byte sequence: 0xFF 0xFE 0x00 0x00 (which is -1, -2, 0, 0 as signed bytes).
     */
    @Test(timeout = 4000)
    public void getBytesReturnsUtf32LeByteOrderMark() throws Throwable {
        ByteOrderMark utf32LeBom = ByteOrderMark.UTF_32LE;

        byte[] actualBytes = utf32LeBom.getBytes();

        byte[] expectedBytes = { (byte) 0xFF, (byte) 0xFE, (byte) 0x00, (byte) 0x00 };
        assertArrayEquals(expectedBytes, actualBytes);
    }
}
