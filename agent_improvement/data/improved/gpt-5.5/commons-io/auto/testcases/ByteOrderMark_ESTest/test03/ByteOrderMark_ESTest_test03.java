package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test03 extends ByteOrderMark_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        ByteOrderMark utf32BigEndianBom = ByteOrderMark.UTF_32BE;

        boolean matchesNullBytes = utf32BigEndianBom.matches((int[]) null);

        assertFalse(matchesNullBytes);
    }
}
