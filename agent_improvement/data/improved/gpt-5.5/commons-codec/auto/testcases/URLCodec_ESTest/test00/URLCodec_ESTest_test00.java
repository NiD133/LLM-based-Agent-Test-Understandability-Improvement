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
public class URLCodec_ESTest_test00 extends URLCodec_ESTest_scaffolding {

    private static final String INVALID_CHARSET_NAME = "Invalid URL encoding: ";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        URLCodec codecWithInvalidCharset = new URLCodec(INVALID_CHARSET_NAME);

        try {
            codecWithInvalidCharset.encode((Object) INVALID_CHARSET_NAME);
            fail("Expecting exception: Exception");
        } catch (Exception expectedException) {
            //
            // Invalid URL encoding:
            //
            verifyException("org.apache.commons.codec.net.URLCodec", expectedException);
        }
    }
}
