package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test09 extends ByteOrderMark_ESTest_scaffolding {

    // UTF_16BE (0xFE, 0xFF) and UTF_32LE (0xFF, 0xFE, 0x00, 0x00) differ in both
    // length and byte values, so equals() must return false.
    @Test(timeout = 4000)
    public void test_equalsReturnsFalse_whenComparingUtf16BeAndUtf32Le() throws Throwable {
        ByteOrderMark utf16be = ByteOrderMark.UTF_16BE;
        ByteOrderMark utf32le = ByteOrderMark.UTF_32LE;

        boolean areEqual = utf16be.equals(utf32le);

        assertFalse(areEqual);
    }
}
