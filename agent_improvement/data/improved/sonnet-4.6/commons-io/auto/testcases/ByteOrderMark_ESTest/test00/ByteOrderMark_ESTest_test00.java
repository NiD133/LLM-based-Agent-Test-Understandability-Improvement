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

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Construct a custom BOM with a non-empty byte array to satisfy the constructor contract
        int[] eightZeroBytes = new int[8];
        ByteOrderMark customBom = new ByteOrderMark("w-", eightZeroBytes);

        // UTF_16LE is a static constant; the toString() format lists its BOM bytes (0xFF, 0xFE)
        String utf16LeBomString = customBom.UTF_16LE.toString();
        assertEquals("ByteOrderMark[UTF-16LE: 0xFF,0xFE]", utf16LeBomString);
    }
}
