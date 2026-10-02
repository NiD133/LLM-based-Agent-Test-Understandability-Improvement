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
public class BCodec_ESTest_test10 extends BCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding a string that is not a valid RFC 1522 encoded word
     * throws an exception indicating malformed encoded content.
     *
     * RFC 1522 encoded words must follow the pattern =?charset?encoding?text?=.
     * A plain string like ")1Y'}:,$Nj&:wqC" does not conform to this format,
     * so RFC1522Codec should reject it with a violation message.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BCodec bCodec = new BCodec();
        String malformedEncodedWord = ")1Y'}:,$Nj&:wqC";

        try {
            bCodec.decode((Object) malformedEncodedWord);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // RFC1522Codec detects that the input is not a valid encoded word
            verifyException("org.apache.commons.codec.net.RFC1522Codec", e);
        }
    }
}
