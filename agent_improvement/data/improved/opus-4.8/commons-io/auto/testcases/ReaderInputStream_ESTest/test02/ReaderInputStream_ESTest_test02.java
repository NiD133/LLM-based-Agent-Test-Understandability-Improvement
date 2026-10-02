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
public class ReaderInputStream_ESTest_test02 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * A null CharsetEncoder falls back to the default charset encoder, so the first
     * read() returns the unsigned byte value of the first character. For the input
     * "{/", that first character is '{', whose code point is 123.
     */
    @Test(timeout = 4000)
    public void readReturnsUnsignedByteOfFirstCharWhenEncoderIsNull() throws Throwable {
        StringReader sourceReader = new StringReader("{/");
        ReaderInputStream readerInputStream = new ReaderInputStream(sourceReader, (CharsetEncoder) null);

        int firstByte = readerInputStream.read();

        int expectedByteForOpenBrace = '{'; // 123
        assertEquals(expectedByteForOpenBrace, firstByte);
    }
}
