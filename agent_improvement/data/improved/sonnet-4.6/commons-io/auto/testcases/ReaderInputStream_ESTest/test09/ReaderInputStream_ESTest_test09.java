package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.PipedReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test09 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that a ReaderInputStream constructed with a PipedReader and the default charset
     * exposes a CharsetEncoder whose maxBytesPerChar matches the expected value for the default charset.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();
        PipedReader reader = new PipedReader(3144);
        ReaderInputStream inputStream = new ReaderInputStream(reader, defaultCharset);

        CharsetEncoder encoder = inputStream.getCharsetEncoder();

        assertEquals(3.0F, encoder.maxBytesPerChar(), 0.01F);
    }
}
