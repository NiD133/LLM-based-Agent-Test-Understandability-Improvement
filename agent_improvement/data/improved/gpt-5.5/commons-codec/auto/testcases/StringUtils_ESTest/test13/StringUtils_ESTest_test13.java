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
public class StringUtils_ESTest_test13 extends StringUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        String emptyInput = "";

        byte[] utf16Bytes = StringUtils.getBytesUtf16(emptyInput);

        assertEquals("UTF-16 encoding an empty string should produce an empty byte array", 0, utf16Bytes.length);
    }
}
