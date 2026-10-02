package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test11 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Setting a custom CharsetEncoder on the builder must not change the
     * builder's buffer size, which stays at the default of 8192.
     */
    @Test(timeout = 4000)
    public void settingCharsetEncoderKeepsDefaultBufferSize() throws Throwable {
        ReaderInputStream.Builder builder = ReaderInputStream.builder();
        CharsetEncoder defaultEncoder = Charset.defaultCharset().newEncoder();

        ReaderInputStream.Builder sameBuilder = builder.setCharsetEncoder(defaultEncoder);

        assertEquals(8192, sameBuilder.getBufferSize());
    }
}
