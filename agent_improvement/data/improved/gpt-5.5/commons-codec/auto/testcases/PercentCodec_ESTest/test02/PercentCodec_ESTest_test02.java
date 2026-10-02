package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test02 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        byte[] alwaysEncodeChars = new byte[1];
        alwaysEncodeChars[0] = (byte) (-43);
        PercentCodec percentCodec = null;

        try {
            percentCodec = new PercentCodec(alwaysEncodeChars, false);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // byte must be >= 0
            //
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
