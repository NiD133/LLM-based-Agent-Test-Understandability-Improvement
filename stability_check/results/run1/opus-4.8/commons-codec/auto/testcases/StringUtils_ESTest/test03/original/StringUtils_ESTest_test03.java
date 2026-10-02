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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Undeclared exception!
        try {
            StringUtils.getBytesUnchecked("", "(![FyY^jueCk Sf|!,w");
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // (![FyY^jueCk Sf|!,w: java.io.UnsupportedEncodingException: (![FyY^jueCk Sf|!,w
            //
            verifyException("org.apache.commons.codec.binary.StringUtils", e);
        }
    }
}
