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
public class ReaderInputStream_ESTest_test04 extends ReaderInputStream_ESTest_scaffolding {

    private static final String EMPTY_INPUT = "";
    private static final long SKIP_DISTANCE_PAST_END_OF_STREAM = 727L;
    private static final long NO_BYTES_SKIPPED = 0L;
    private static final int END_OF_STREAM = -1;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        StringReader emptyReader = new StringReader(EMPTY_INPUT);
        Charset defaultCharset = Charset.defaultCharset();
        CharsetEncoder defaultCharsetEncoder = defaultCharset.newEncoder();
        ReaderInputStream stream = new ReaderInputStream(emptyReader, defaultCharsetEncoder);

        long skippedByteCount = stream.skip(SKIP_DISTANCE_PAST_END_OF_STREAM);
        assertEquals(NO_BYTES_SKIPPED, skippedByteCount);

        int byteReadAfterSkippingEmptyInput = stream.read();
        assertEquals(END_OF_STREAM, byteReadAfterSkippingEmptyInput);
    }
}
