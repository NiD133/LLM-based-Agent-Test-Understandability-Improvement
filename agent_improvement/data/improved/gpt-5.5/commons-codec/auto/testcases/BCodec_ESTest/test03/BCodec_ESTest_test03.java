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
public class BCodec_ESTest_test03 extends BCodec_ESTest_scaffolding {

    private static final String B_CODEC_CLASS_NAME = "org.apache.commons.codec.net.BCodec";

    @Test(timeout = 4000)
    public void testEncodeRejectsBCodecObject() throws Throwable {
        BCodec codec = new BCodec();

        try {
            codec.encode((Object) codec);
            fail("Expecting exception: Exception");
        } catch (Exception exception) {
            //
            // Objects of type org.apache.commons.codec.net.BCodec cannot be encoded using BCodec
            //
            verifyException(B_CODEC_CLASS_NAME, exception);
        }
    }
}
