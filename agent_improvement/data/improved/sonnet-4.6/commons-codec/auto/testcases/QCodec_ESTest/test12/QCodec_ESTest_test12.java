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
public class QCodec_ESTest_test12 extends QCodec_ESTest_scaffolding {

    /**
     * Verifies that encode(String, String) throws an exception when the
     * supplied charset name is not a valid charset (here, a class name is
     * passed instead of a real charset identifier such as "UTF-8").
     * The exception is expected to originate from QCodec itself.
     */
    @Test(timeout = 4000)
    public void test_encodeWithInvalidCharsetName_throwsException() throws Throwable {
        QCodec qCodec = new QCodec();
        String emptyInput = "";
        String invalidCharsetName = "org.apache.commons.codec.net.QCodec"; // not a charset name

        try {
            qCodec.encode(emptyInput, invalidCharsetName);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.net.QCodec", e);
        }
    }
}
