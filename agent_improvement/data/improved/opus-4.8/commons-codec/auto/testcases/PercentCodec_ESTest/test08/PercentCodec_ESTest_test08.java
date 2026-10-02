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

    /**
     * decode(Object) only accepts byte[] arguments. Passing a plain Object
     * that is not a byte[] must throw a DecoderException reporting that the
     * type cannot be Percent decoded.
     */
    @Test(timeout = 4000)
    public void decodeRejectsNonByteArrayObject() throws Throwable {
        byte[] alwaysEncodeChars = new byte[1];
        PercentCodec percentCodec = new PercentCodec(alwaysEncodeChars, true);
        Object nonByteArrayInput = new Object();

        try {
            percentCodec.decode(nonByteArrayInput);
            fail("Expected an exception because a plain Object is not a byte[]");
        } catch (Exception e) {
            // Message: "Objects of type java.lang.Object cannot be Percent decoded"
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
