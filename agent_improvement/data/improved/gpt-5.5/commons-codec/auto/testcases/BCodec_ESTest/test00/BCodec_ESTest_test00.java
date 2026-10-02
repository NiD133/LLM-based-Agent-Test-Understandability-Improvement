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

    private static final String EMPTY_VALUE_TO_ENCODE = "";
    private static final String UNSUPPORTED_CHARSET_NAME = "org.apache.commons.codec.binary.Base64";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        BCodec codec = new BCodec();

        try {
            codec.encode(EMPTY_VALUE_TO_ENCODE, UNSUPPORTED_CHARSET_NAME);
            fail("Expecting exception: Exception");
        } catch (Exception exception) {
            verifyException("org.apache.commons.codec.net.BCodec", exception);
        }
    }
}
