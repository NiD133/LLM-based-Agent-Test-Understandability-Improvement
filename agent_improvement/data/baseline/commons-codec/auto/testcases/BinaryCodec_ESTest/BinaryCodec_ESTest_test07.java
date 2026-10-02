package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test07 extends BinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        String string0 = BinaryCodec.toAsciiString((byte[]) null);
        assertEquals("", string0);
    }
}
