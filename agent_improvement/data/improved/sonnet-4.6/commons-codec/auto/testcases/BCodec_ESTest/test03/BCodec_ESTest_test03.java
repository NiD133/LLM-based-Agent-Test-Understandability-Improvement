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

    /**
     * BCodec.encode(Object) only accepts String values; passing any other type
     * must throw an EncoderException with a message identifying the unsupported type.
     */
    @Test(timeout = 4000)
    public void test_encodeObject_withNonStringObject_throwsEncoderException() throws Throwable {
        BCodec codec = new BCodec();
        try {
            // A BCodec instance is not a String, so encode(Object) must reject it.
            codec.encode((Object) codec);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Objects of type org.apache.commons.codec.net.BCodec cannot be encoded using BCodec
            //
            verifyException("org.apache.commons.codec.net.BCodec", e);
        }
    }
}
