package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test09 extends ReaderInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Charset charset0 = Charset.defaultCharset();
        PipedReader pipedReader0 = new PipedReader(3144);
        ReaderInputStream readerInputStream0 = new ReaderInputStream(pipedReader0, charset0);
        CharsetEncoder charsetEncoder0 = readerInputStream0.getCharsetEncoder();
        assertEquals(3.0F, charsetEncoder0.maxBytesPerChar(), 0.01F);
    }
}
