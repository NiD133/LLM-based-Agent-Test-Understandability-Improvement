package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test10 extends ReaderInputStream_ESTest_scaffolding {

    // ReaderInputStream.available() is documented to always return 0,
    // because the number of readable bytes cannot be predicted without consuming the Reader.
    @Test(timeout = 4000)
    public void test_available_returnsZeroBeforeAnyRead() throws Throwable {
        StringReader reader = new StringReader("3t8FoIT,\",/");
        CharsetEncoder encoder = Charset.defaultCharset().newEncoder();
        ReaderInputStream inputStream = new ReaderInputStream(reader, encoder);

        int availableBytes = inputStream.available();

        assertEquals(0, availableBytes);
    }
}
