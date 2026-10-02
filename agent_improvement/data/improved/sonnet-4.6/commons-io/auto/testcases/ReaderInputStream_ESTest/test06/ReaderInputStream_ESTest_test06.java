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
     * A buffer size below the minimum required for the charset encoder (at least 6 bytes for UTF-8)
     * must cause the constructor to throw IllegalArgumentException immediately.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();
        int invalidBufferSize = -4369;

        try {
            new ReaderInputStream((Reader) null, defaultCharset, invalidBufferSize);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Buffer size -4,369 must be at least 6.0 for a CharsetEncoder UTF-8.
            //
            verifyException("org.apache.commons.io.input.ReaderInputStream", e);
        }
    }
}
