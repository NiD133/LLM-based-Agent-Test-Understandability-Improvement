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

    /**
     * Verifies that two distinct BOM constants whose byte sequences differ are
     * not considered equal. UTF-16BE (0xFE 0xFF) and UTF-32LE (0xFF 0xFE 0x00 0x00)
     * have different lengths and bytes, so equals() must return false.
     */
    @Test(timeout = 4000)
    public void testEqualsReturnsFalseForDifferentBoms() throws Throwable {
        ByteOrderMark utf16Be = ByteOrderMark.UTF_16BE;
        ByteOrderMark utf32Le = ByteOrderMark.UTF_32LE;

        boolean bomsAreEqual = utf16Be.equals(utf32Le);

        assertFalse(bomsAreEqual);
    }
}
