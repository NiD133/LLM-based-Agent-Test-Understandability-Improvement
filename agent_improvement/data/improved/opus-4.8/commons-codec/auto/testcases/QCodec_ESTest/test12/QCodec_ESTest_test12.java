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
     * Encoding with a charset name that does not name any installed charset
     * must fail: QCodec.encode(String, String) wraps the resulting
     * UnsupportedCharsetException in an exception thrown from QCodec itself.
     */
    @Test(timeout = 4000)
    public void encodeWithUnknownCharsetNameThrowsException() throws Throwable {
        QCodec qCodec = new QCodec();
        String invalidCharsetName = "org.apache.commons.codec.net.QCodec";

        try {
            qCodec.encode("", invalidCharsetName);
            fail("Expected an exception because the charset name is not a valid charset");
        } catch (Exception e) {
            // The exception originates from QCodec when the charset name cannot be resolved.
            verifyException("org.apache.commons.codec.net.QCodec", e);
        }
    }
}
