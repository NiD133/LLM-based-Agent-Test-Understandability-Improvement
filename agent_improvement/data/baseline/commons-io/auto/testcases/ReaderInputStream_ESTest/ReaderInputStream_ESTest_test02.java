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

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        StringReader stringReader0 = new StringReader("{/");
        ReaderInputStream readerInputStream0 = new ReaderInputStream(stringReader0, (CharsetEncoder) null);
        int int0 = readerInputStream0.read();
        assertEquals(123, int0);
    }
}
