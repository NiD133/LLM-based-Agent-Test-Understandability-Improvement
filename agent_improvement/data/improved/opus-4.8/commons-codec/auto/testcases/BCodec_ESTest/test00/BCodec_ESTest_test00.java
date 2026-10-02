package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BCodec_ESTest_test00 extends BCodec_ESTest_scaffolding {

    /**
     * Encoding with a charset name that is not a real charset must fail.
     * Here the second argument ("org.apache.commons.codec.binary.Base64") is a
     * class name, not a charset name, so BCodec cannot resolve a Charset for it
     * and propagates an exception from within BCodec.
     */
    @Test(timeout = 4000)
    public void encodeWithUnknownCharsetNameThrowsException() throws Throwable {
        BCodec bCodec = new BCodec();
        String invalidCharsetName = "org.apache.commons.codec.binary.Base64";

        try {
            bCodec.encode("", invalidCharsetName);
            fail("Expected an exception because the charset name is invalid");
        } catch (Exception e) {
            // The failure must originate from BCodec itself.
            verifyException("org.apache.commons.codec.net.BCodec", e);
        }
    }
}
