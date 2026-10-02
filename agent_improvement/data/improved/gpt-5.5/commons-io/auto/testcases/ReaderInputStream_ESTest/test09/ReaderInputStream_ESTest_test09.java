package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test09 extends ReaderInputStream_ESTest_scaffolding {

    private static final int PIPED_READER_BUFFER_SIZE = 3144;
    private static final float EXPECTED_MAX_BYTES_PER_CHAR = 3.0F;
    private static final float FLOAT_COMPARISON_DELTA = 0.01F;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();
        PipedReader pipedReader = new PipedReader(PIPED_READER_BUFFER_SIZE);
        ReaderInputStream readerInputStream = new ReaderInputStream(pipedReader, defaultCharset);

        CharsetEncoder charsetEncoder = readerInputStream.getCharsetEncoder();

        assertEquals(EXPECTED_MAX_BYTES_PER_CHAR, charsetEncoder.maxBytesPerChar(), FLOAT_COMPARISON_DELTA);
    }
}
