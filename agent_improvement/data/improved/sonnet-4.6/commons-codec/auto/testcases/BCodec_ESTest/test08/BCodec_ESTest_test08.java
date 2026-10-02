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
public class BCodec_ESTest_test08 extends BCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding a non-String object (a BCodec instance) throws a DecoderException,
     * because BCodec.decode(Object) only accepts String arguments.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        BCodec codec = new BCodec();
        try {
            // Passing a BCodec instance where a String is expected — must throw
            codec.decode((Object) codec);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.net.BCodec", e);
        }
    }
}
