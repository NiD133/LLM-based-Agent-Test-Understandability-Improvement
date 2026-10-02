package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test00 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that the UTF-16LE BOM constant produces the correct string representation
     * showing its charset name and byte values (0xFF, 0xFE).
     */
    @Test(timeout = 4000)
    public void test_UTF16LE_toString_returnsExpectedBomFormat() throws Throwable {
        // Create a custom ByteOrderMark with an 8-byte zero-filled array to satisfy the constructor
        int[] zeroBomBytes = new int[8];
        ByteOrderMark customBom = new ByteOrderMark("w-", zeroBomBytes);

        // The static UTF_16LE constant represents the Little-Endian BOM: bytes 0xFF, 0xFE
        String utf16leString = customBom.UTF_16LE.toString();

        assertEquals("ByteOrderMark[UTF-16LE: 0xFF,0xFE]", utf16leString);
    }
}
