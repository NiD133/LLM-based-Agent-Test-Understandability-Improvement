package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test01 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that decodeUrl throws a DecoderException when the input ends with
     * a bare '%' escape character that has no following two hex digits to decode.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Last byte is '%' (the URL percent-escape marker), but no hex digits follow —
        // this is an incomplete/truncated percent-encoded sequence.
        byte[] bytesEndingWithPercentSign = new byte[5];
        bytesEndingWithPercentSign[4] = (byte) '%';

        try {
            URLCodec.decodeUrl(bytesEndingWithPercentSign);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // DecoderException("Invalid URL encoding: ") is expected because the '%'
            // at position 4 tries to read two more bytes that do not exist.
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
