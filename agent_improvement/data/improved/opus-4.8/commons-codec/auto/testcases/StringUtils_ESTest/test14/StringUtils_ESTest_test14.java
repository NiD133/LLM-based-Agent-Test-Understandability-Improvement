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
public class StringUtils_ESTest_test14 extends StringUtils_ESTest_scaffolding {

    /**
     * Encoding a String to UTF-8 yields a heap-backed (non-direct) ByteBuffer,
     * since the implementation wraps a freshly allocated byte array.
     */
    @Test(timeout = 4000)
    public void getByteBufferUtf8_returnsHeapBackedBuffer() throws Throwable {
        ByteBuffer utf8Buffer = StringUtils.getByteBufferUtf8("&Fz(oNy*n^S*wa-O?h");

        assertFalse("Buffer wrapping a byte array must not be direct", utf8Buffer.isDirect());
    }
}
