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
public class URLCodec_ESTest_test09 extends URLCodec_ESTest_scaffolding {

    /**
     * URLCodec.decode(Object) only accepts String or byte[] arguments. Passing
     * any other object type (here, the URLCodec instance itself) must be rejected
     * with a DecoderException reporting that the type cannot be URL decoded.
     */
    @Test(timeout = 4000)
    public void decodeUnsupportedObjectTypeThrowsException() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        try {
            urlCodec.decode((Object) urlCodec);
            fail("Expected an exception: a URLCodec object is not a valid type for decoding");
        } catch (Exception e) {
            // Objects of type org.apache.commons.codec.net.URLCodec cannot be URL decoded
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
