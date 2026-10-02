package org.apache.commons.compress.utils;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test05 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteUtils#fromLittleEndian(InputStream, int)} throws an
     * {@link IOException} when the stream runs out of bytes before the requested
     * number of bytes has been read.
     *
     * <p>The stream is positioned so that only a single byte is available, yet eight
     * bytes are requested, so the read must fail with "Premature end of data".</p>
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_throwsWhenStreamHasFewerBytesThanRequested() throws Throwable {
        // A 9-byte buffer, but the stream is restricted to just the last byte (offset 8, length 1).
        byte[] buffer = new byte[9];
        InputStream streamWithOneByte = new ByteArrayInputStream(buffer, 8, 1);

        int requestedByteCount = 8;
        try {
            ByteUtils.fromLittleEndian(streamWithOneByte, requestedByteCount);
            fail("Expected an IOException because the stream cannot supply " + requestedByteCount + " bytes");
        } catch (IOException e) {
            // Thrown by ByteUtils once the stream is exhausted ("Premature end of data").
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
