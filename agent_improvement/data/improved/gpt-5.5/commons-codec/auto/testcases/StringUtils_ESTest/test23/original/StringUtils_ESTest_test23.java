package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test23 extends StringUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        byte[] byteArray0 = new byte[4];
        // Undeclared exception!
        try {
            StringUtils.newString(byteArray0, "\u00DA\u0000");
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // \u00DA\u0000: java.io.UnsupportedEncodingException: \u00DA\u0000
            //
            verifyException("org.apache.commons.codec.binary.StringUtils", e);
        }
    }
}
