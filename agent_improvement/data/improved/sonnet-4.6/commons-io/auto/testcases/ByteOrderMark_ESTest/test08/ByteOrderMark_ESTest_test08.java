package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test08 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * UTF_16BE (big-endian: 0xFE 0xFF) and UTF_16LE (little-endian: 0xFF 0xFE)
     * have different byte sequences and must not be considered equal to each other.
     */
    @Test(timeout = 4000)
    public void test08_differentEndianBOMsAreNotEqual() throws Throwable {
        ByteOrderMark utf16BigEndian    = ByteOrderMark.UTF_16BE;
        ByteOrderMark utf16LittleEndian = ByteOrderMark.UTF_16LE;

        assertFalse("UTF-16BE should not equal UTF-16LE",
                utf16BigEndian.equals(utf16LittleEndian));
        assertFalse("UTF-16LE should not equal UTF-16BE (symmetry check)",
                utf16LittleEndian.equals((Object) utf16BigEndian));
    }
}
