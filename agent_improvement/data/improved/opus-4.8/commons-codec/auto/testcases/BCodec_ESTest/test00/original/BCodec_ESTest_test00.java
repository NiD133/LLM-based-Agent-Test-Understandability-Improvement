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

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        BCodec bCodec0 = new BCodec();
        try {
            bCodec0.encode("", "org.apache.commons.codec.binary.Base64");
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // org.apache.commons.codec.binary.Base64
            //
            verifyException("org.apache.commons.codec.net.BCodec", e);
        }
    }
}
