package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test06 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that calling hashCode() on the predefined UTF-16 Big-Endian BOM constant
     * completes without throwing an exception.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // UTF_16BE represents the UTF-16 Big-Endian BOM, identified by bytes 0xFE 0xFF
        ByteOrderMark utf16BigEndianBom = ByteOrderMark.UTF_16BE;
        utf16BigEndianBom.hashCode();
    }
}
