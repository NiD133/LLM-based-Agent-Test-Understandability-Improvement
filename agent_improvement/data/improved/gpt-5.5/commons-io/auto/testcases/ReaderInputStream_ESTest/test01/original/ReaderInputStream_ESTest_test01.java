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
public class ReaderInputStream_ESTest_test01 extends ReaderInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        ReaderInputStream.Builder readerInputStream_Builder0 = ReaderInputStream.builder();
        byte[] byteArray0 = new byte[4];
        readerInputStream_Builder0.setByteArray(byteArray0);
        ReaderInputStream readerInputStream0 = readerInputStream_Builder0.get();
        assertFalse(readerInputStream0.isClosed());
    }
}
