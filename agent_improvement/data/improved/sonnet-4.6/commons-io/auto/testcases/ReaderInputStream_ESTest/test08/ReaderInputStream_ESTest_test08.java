package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test08 extends ReaderInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        ReaderInputStream.Builder builder = new ReaderInputStream.Builder();
        CharsetEncoder defaultEncoder = builder.getCharsetEncoder();
        // The default charset (UTF-8) encoder allows at most 3 bytes per character
        assertEquals(3.0F, defaultEncoder.maxBytesPerChar(), 0.01F);
    }
}
