package org.apache.commons.codec.binary;

import static org.junit.Assert.assertNull;

import java.nio.ByteBuffer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test04 extends StringUtils_ESTest_scaffolding {

    /**
     * Encoding a {@code null} string to a UTF-8 {@link ByteBuffer} should
     * return {@code null} rather than throw, mirroring the contract of
     * {@link StringUtils#getByteBufferUtf8(String)}.
     */
    @Test(timeout = 4000)
    public void getByteBufferUtf8_withNullString_returnsNull() throws Throwable {
        ByteBuffer encoded = StringUtils.getByteBufferUtf8((String) null);

        assertNull("Encoding a null string must yield a null buffer", encoded);
    }
}
