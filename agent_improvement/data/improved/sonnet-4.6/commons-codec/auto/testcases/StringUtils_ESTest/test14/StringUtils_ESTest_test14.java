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

    @Test(timeout = 4000)
    public void test_getByteBufferUtf8_returnsHeapBackedBuffer() throws Throwable {
        // getByteBufferUtf8 wraps a byte array via ByteBuffer.wrap, which produces
        // a heap-backed (non-direct) buffer rather than a native-memory buffer.
        ByteBuffer utf8EncodedBuffer = StringUtils.getByteBufferUtf8("&Fz(oNy*n^S*wa-O?h");
        assertFalse("ByteBuffer returned by getByteBufferUtf8 should be heap-backed, not direct",
                utf8EncodedBuffer.isDirect());
    }
}
