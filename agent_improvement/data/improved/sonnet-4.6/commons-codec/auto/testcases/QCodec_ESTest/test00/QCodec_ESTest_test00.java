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
public class QCodec_ESTest_test00 extends QCodec_ESTest_scaffolding {

    /**
     * QCodec.encode(Object) only accepts String arguments; passing any other
     * object type (here, a QCodec instance itself) must throw an Exception
     * with a message stating that the type cannot be encoded using Q codec.
     */
    @Test(timeout = 4000)
    public void test_encode_nonStringObject_throwsException() throws Throwable {
        QCodec qCodec = new QCodec();
        try {
            // A QCodec instance is not a String, so encoding it should fail
            qCodec.encode((Object) qCodec);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Expected message: "Objects of type org.apache.commons.codec.net.QCodec cannot be encoded using Q codec"
            verifyException("org.apache.commons.codec.net.QCodec", e);
        }
    }
}
