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
     * Two BOMs with different bytes (UTF-16 big-endian vs little-endian) are not
     * equal to each other, and equality is symmetric in both directions.
     */
    @Test(timeout = 4000)
    public void equals_differentBoms_returnsFalseBothWays() throws Throwable {
        ByteOrderMark utf16BigEndian = ByteOrderMark.UTF_16BE;
        ByteOrderMark utf16LittleEndian = ByteOrderMark.UTF_16LE;

        assertFalse(utf16BigEndian.equals(utf16LittleEndian));
        assertFalse(utf16LittleEndian.equals((Object) utf16BigEndian));
    }
}
