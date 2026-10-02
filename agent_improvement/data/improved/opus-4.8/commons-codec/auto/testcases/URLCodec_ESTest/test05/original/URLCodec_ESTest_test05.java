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
public class URLCodec_ESTest_test05 extends URLCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        URLCodec uRLCodec0 = new URLCodec();
        long[] longArray0 = new long[2];
        BitSet bitSet0 = BitSet.valueOf(longArray0);
        try {
            uRLCodec0.encode((Object) bitSet0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Objects of type java.util.BitSet cannot be URL encoded
            //
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
