package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test08 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        byte[] byteArray0 = new byte[1];
        PercentCodec percentCodec0 = new PercentCodec(byteArray0, true);
        Object object0 = new Object();
        try {
            percentCodec0.decode(object0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Objects of type java.lang.Object cannot be Percent decoded
            //
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
