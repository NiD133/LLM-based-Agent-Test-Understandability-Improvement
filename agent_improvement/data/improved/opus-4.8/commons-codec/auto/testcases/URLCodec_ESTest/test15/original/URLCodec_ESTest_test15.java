package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.BitSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test15 extends URLCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        URLCodec uRLCodec0 = new URLCodec();
        try {
            uRLCodec0.decode((Object) "*aAC%+");
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Invalid URL encoding: not a valid digit (radix 16): 43
            //
            verifyException("org.apache.commons.codec.net.Utils", e);
        }
    }
}
