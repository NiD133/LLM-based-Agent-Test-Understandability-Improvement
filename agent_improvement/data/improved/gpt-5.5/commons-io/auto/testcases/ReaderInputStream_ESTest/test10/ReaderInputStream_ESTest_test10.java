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

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        StringReader sourceReader = new StringReader("3t8FoIT,\",/");
        Charset defaultCharset = Charset.defaultCharset();
        CharsetEncoder defaultCharsetEncoder = defaultCharset.newEncoder();
        ReaderInputStream inputStream = new ReaderInputStream(sourceReader, defaultCharsetEncoder);

        int availableBytesBeforeReading = inputStream.available();

        assertEquals(0, availableBytesBeforeReading);
    }
}
