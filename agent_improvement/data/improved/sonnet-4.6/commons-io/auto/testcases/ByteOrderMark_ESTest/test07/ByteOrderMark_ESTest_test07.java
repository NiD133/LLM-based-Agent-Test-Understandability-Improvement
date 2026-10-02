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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // UTF-32LE BOM is defined as the byte sequence: 0xFF 0xFE 0x00 0x00
        byte[] expectedUtf32leBytes = { (byte) 0xFF, (byte) 0xFE, (byte) 0x00, (byte) 0x00 };

        byte[] actualBytes = ByteOrderMark.UTF_32LE.getBytes();

        assertArrayEquals(expectedUtf32leBytes, actualBytes);
    }
}
