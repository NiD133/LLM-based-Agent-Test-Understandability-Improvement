package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.Reader;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test06 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * The (Reader, Charset, int) constructor must reject a buffer size that is
     * too small for the charset's encoder. A negative size like -4369 is well
     * below the minimum (2 * maxBytesPerChar), so construction must fail with
     * an IllegalArgumentException rather than producing an unusable stream.
     */
    @Test(timeout = 4000)
    public void constructorRejectsBufferSizeBelowEncoderMinimum() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();
        int tooSmallBufferSize = -4369;

        try {
            new ReaderInputStream((Reader) null, defaultCharset, tooSmallBufferSize);
            fail("Expected IllegalArgumentException for a buffer size below the encoder minimum");
        } catch (IllegalArgumentException e) {
            // Message reads e.g. "Buffer size -4,369 must be at least 6.0 for a CharsetEncoder UTF-8."
            verifyException("org.apache.commons.io.input.ReaderInputStream", e);
        }
    }
}
