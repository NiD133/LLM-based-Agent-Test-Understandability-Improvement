package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test07 extends QCodec_ESTest_scaffolding {

    /**
     * Verifies that decode(Object) throws a DecoderException when passed an object
     * that is not a String. Q codec can only decode String instances; passing an
     * incompatible type (here, a QCodec instance itself) must be rejected.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        QCodec codec = new QCodec();
        Object nonStringObject = codec; // QCodec is not a String, so decoding it is unsupported

        try {
            codec.decode(nonStringObject);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Expected message: "Objects of type org.apache.commons.codec.net.QCodec cannot be decoded using Q codec"
            verifyException("org.apache.commons.codec.net.QCodec", e);
        }
    }
}
