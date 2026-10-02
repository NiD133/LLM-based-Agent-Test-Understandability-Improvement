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

    /**
     * Verifies that URLCodec throws an exception when the codec is initialized
     * with an invalid charset name and then asked to encode a string object.
     * The invalid charset causes getBytes() to fail, which URLCodec wraps and rethrows.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // "Invalid URL encoding: " is not a real charset name, so encoding will fail
        URLCodec codecWithInvalidCharset = new URLCodec("Invalid URL encoding: ");
        try {
            codecWithInvalidCharset.encode((Object) "Invalid URL encoding: ");
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
