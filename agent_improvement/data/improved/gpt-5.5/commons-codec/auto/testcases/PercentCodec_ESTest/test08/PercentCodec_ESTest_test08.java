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
        byte[] alwaysEncodeChars = new byte[1];
        PercentCodec codec = new PercentCodec(alwaysEncodeChars, true);
        Object nonByteArrayInput = new Object();

        try {
            codec.decode(nonByteArrayInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
