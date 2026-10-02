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

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        ByteOrderMark utf16BigEndian = ByteOrderMark.UTF_16BE;
        ByteOrderMark utf32LittleEndian = ByteOrderMark.UTF_32LE;

        boolean marksAreEqual = utf16BigEndian.equals(utf32LittleEndian);

        assertFalse(marksAreEqual);
    }
}
