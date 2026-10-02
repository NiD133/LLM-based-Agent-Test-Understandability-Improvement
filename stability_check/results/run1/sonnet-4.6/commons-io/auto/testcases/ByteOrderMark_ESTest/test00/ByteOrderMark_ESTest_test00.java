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
        int[] zeroBytes = new int[8];
        ByteOrderMark customBom = new ByteOrderMark("w-", zeroBytes);

        // Accessing the static UTF_16LE constant via an instance reference (same as ByteOrderMark.UTF_16LE)
        String utf16leString = customBom.UTF_16LE.toString();
        assertEquals("ByteOrderMark[UTF-16LE: 0xFF,0xFE]", utf16leString);
    }
}
