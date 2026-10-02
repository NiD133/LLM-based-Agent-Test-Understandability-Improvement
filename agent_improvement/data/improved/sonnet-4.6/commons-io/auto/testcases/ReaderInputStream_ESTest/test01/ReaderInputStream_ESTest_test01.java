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

    // A stream built via Builder with a byte array source should start in an open (not closed) state
    @Test(timeout = 4000)
    public void test01_builderWithByteArray_streamIsInitiallyOpen() throws Throwable {
        ReaderInputStream.Builder builder = ReaderInputStream.builder();
        byte[] sourceBytes = new byte[4];
        builder.setByteArray(sourceBytes);
        ReaderInputStream stream = builder.get();
        assertFalse(stream.isClosed());
    }
}
