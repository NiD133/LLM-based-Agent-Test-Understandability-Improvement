package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test15 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        byte[] byteArray0 = new byte[5];
        byte[] byteArray1 = new byte[0];
        PercentCodec percentCodec0 = new PercentCodec(byteArray1, false);
        Object object0 = percentCodec0.encode((Object) byteArray0);
        assertSame(byteArray0, object0);
        assertNotNull(object0);
    }
}
