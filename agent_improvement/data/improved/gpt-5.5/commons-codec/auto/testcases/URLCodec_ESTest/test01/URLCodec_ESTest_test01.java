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
public class URLCodec_ESTest_test01 extends URLCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        byte[] encodedBytesEndingWithBareEscape = new byte[5];
        encodedBytesEndingWithBareEscape[4] = (byte) 37;

        try {
            URLCodec.decodeUrl(encodedBytesEndingWithBareEscape);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Invalid URL encoding:
            //
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
