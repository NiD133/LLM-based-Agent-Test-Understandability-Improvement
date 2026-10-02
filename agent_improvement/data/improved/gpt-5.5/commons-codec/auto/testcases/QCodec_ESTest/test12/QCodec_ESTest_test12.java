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

    private static final String EMPTY_SOURCE = "";
    private static final String INVALID_CHARSET_NAME = "org.apache.commons.codec.net.QCodec";

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        QCodec codec = new QCodec();

        try {
            codec.encode(EMPTY_SOURCE, INVALID_CHARSET_NAME);
            fail("Expecting exception: Exception");
        } catch (Exception exception) {
            verifyException(INVALID_CHARSET_NAME, exception);
        }
    }
}
