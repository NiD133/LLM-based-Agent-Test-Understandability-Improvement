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
public class ReaderInputStream_ESTest_test08 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * A freshly created Builder initializes its CharsetEncoder from the default charset.
     * That default encoder reports a maximum of 3.0 bytes per encoded character.
     */
    @Test(timeout = 4000)
    public void builderProvidesDefaultCharsetEncoderWithThreeMaxBytesPerChar() throws Throwable {
        ReaderInputStream.Builder builder = new ReaderInputStream.Builder();

        CharsetEncoder defaultEncoder = builder.getCharsetEncoder();

        float expectedMaxBytesPerChar = 3.0F;
        float tolerance = 0.01F;
        assertEquals(expectedMaxBytesPerChar, defaultEncoder.maxBytesPerChar(), tolerance);
    }
}
