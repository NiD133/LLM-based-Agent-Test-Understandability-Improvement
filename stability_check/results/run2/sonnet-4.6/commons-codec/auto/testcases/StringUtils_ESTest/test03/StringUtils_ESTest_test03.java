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

    // An unrecognized charset name that is guaranteed to be unsupported by the JVM
    private static final String UNSUPPORTED_CHARSET = "(![FyY^jueCk Sf|!,w";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // getBytesUnchecked wraps UnsupportedEncodingException as IllegalStateException
        // when the supplied charset name is not recognized by the JVM
        try {
            StringUtils.getBytesUnchecked("", UNSUPPORTED_CHARSET);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.codec.binary.StringUtils", e);
        }
    }
}
