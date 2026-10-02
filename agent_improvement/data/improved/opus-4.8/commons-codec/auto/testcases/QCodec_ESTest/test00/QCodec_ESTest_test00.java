package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test00 extends QCodec_ESTest_scaffolding {

    /**
     * QCodec.encode(Object) only accepts String arguments. Passing any other
     * object type (here, the QCodec instance itself) must raise an
     * EncoderException reporting that the object cannot be encoded.
     */
    @Test(timeout = 4000)
    public void encodeNonStringObjectThrowsEncoderException() throws Throwable {
        QCodec qCodec = new QCodec();

        try {
            qCodec.encode((Object) qCodec);
            fail("Expected an exception because a QCodec instance is not a String and cannot be Q-encoded");
        } catch (Exception e) {
            // Message: "Objects of type org.apache.commons.codec.net.QCodec cannot be encoded using Q codec"
            verifyException("org.apache.commons.codec.net.QCodec", e);
        }
    }
}
