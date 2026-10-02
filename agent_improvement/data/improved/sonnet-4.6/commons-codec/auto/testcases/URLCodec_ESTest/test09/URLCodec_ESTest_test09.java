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
     * Verifies that passing a URLCodec instance (an unsupported type) to decode(Object)
     * throws a DecoderException, since only String and byte[] are accepted inputs.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        URLCodec codec = new URLCodec();
        try {
            codec.decode((Object) codec);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Objects of type org.apache.commons.codec.net.URLCodec cannot be URL decoded
            //
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
