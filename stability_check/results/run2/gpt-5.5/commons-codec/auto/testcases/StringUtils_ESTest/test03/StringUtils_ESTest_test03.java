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
public class StringUtils_ESTest_test03 extends StringUtils_ESTest_scaffolding {

    private static final String EMPTY_INPUT = "";
    private static final String UNSUPPORTED_CHARSET_NAME = "(![FyY^jueCk Sf|!,w";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        try {
            StringUtils.getBytesUnchecked(EMPTY_INPUT, UNSUPPORTED_CHARSET_NAME);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException exception) {
            verifyException("org.apache.commons.codec.binary.StringUtils", exception);
        }
    }
}
