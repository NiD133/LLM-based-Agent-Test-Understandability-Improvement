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
public class ReaderInputStream_ESTest_test10 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that {@link ReaderInputStream#available()} reports 0 bytes on a
     * freshly constructed stream. As documented, ReaderInputStream always
     * returns 0 from available() because no bytes have been encoded into its
     * internal output buffer until the first read occurs.
     */
    @Test(timeout = 4000)
    public void availableReturnsZeroBeforeAnyRead() throws Throwable {
        StringReader sourceReader = new StringReader("3t8FoIT,\",/");
        CharsetEncoder defaultEncoder = Charset.defaultCharset().newEncoder();
        ReaderInputStream readerInputStream = new ReaderInputStream(sourceReader, defaultEncoder);

        int availableBytes = readerInputStream.available();

        assertEquals(0, availableBytes);
    }
}
