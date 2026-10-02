package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test03 extends PercentCodec_ESTest_scaffolding {

    // encode(Object) only accepts byte[] — passing any other type must throw EncoderException
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        byte[] alwaysEncodeChars = new byte[1];
        PercentCodec codec = new PercentCodec(alwaysEncodeChars, true);
        try {
            codec.encode((Object) codec);
            fail("Expecting exception: Exception");
        } catch (Exception encoderException) {
            verifyException("org.apache.commons.codec.net.PercentCodec", encoderException);
        }
    }
}
