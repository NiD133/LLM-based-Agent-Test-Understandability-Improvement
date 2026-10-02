package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BCodec_ESTest_test10 extends BCodec_ESTest_scaffolding {

    /**
     * Decoding a string that is not a valid RFC 1522 encoded-word should fail.
     * The input below lacks the required "=?charset?encoding?...?=" wrapper, so
     * RFC1522Codec rejects it before any Base64 decoding can take place.
     */
    @Test(timeout = 4000)
    public void decodeMalformedEncodedWordThrowsException() throws Throwable {
        BCodec bCodec = new BCodec();
        String malformedEncodedWord = ")1Y'}:,$Nj&:wqC";

        try {
            bCodec.decode((Object) malformedEncodedWord);
            fail("Expected an exception for an RFC 1522 violation (malformed encoded content)");
        } catch (Exception e) {
            // RFC 1522 violation: malformed encoded content is detected by RFC1522Codec.
            verifyException("org.apache.commons.codec.net.RFC1522Codec", e);
        }
    }
}
