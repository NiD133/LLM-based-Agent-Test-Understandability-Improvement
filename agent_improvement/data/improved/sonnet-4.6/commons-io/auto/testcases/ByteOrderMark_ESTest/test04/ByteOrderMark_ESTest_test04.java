package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test04 extends ByteOrderMark_ESTest_scaffolding {

    // matches() returns true immediately when the same array reference is passed (identity shortcut in the implementation)
    @Test(timeout = 4000)
    public void test_matches_returnsTrueForSameRawByteArrayReference() throws Throwable {
        ByteOrderMark utf32be = ByteOrderMark.UTF_32BE;
        int[] rawBytes = utf32be.getRawBytes();
        boolean result = utf32be.matches(rawBytes);
        assertTrue(result);
    }
}
