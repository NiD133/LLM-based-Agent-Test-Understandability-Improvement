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
public class ReaderInputStream_ESTest_test13 extends ReaderInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        PipedReader unconnectedReader = new PipedReader();
        ReaderInputStream inputStream = new ReaderInputStream(unconnectedReader);
        byte[] emptyBuffer = new byte[0];

        // Reading into an empty buffer should immediately report zero bytes read.
        int bytesRead = inputStream.read(emptyBuffer);

        assertEquals(0, bytesRead);
    }
}
