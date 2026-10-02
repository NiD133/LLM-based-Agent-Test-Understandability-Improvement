package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test12 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        PercentCodec percentCodec0 = new PercentCodec();
        byte[] byteArray0 = percentCodec0.decode((byte[]) null);
        assertNull(byteArray0);
    }
}
