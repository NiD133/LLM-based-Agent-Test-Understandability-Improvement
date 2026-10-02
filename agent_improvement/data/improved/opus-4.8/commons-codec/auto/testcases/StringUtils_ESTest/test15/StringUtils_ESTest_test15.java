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
public class StringUtils_ESTest_test15 extends StringUtils_ESTest_scaffolding {

    /**
     * UTF-8 encoding an empty string should produce an empty byte array.
     */
    @Test(timeout = 4000)
    public void getBytesUtf8_withEmptyString_returnsEmptyByteArray() throws Throwable {
        byte[] encodedBytes = StringUtils.getBytesUtf8("");

        assertArrayEquals(new byte[] {}, encodedBytes);
    }
}
