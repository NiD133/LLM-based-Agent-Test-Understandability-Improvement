package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test07 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Setting a charset on the builder must not change the buffer size, which
     * should remain at the default of 8192 characters.
     */
    @Test(timeout = 4000)
    public void settingCharsetKeepsDefaultBufferSize() throws Throwable {
        ReaderInputStream.Builder builder = new ReaderInputStream.Builder();

        ReaderInputStream.Builder builderWithCharset = builder.setCharset(Charset.defaultCharset());

        assertEquals(8192, builderWithCharset.getBufferSize());
    }
}
