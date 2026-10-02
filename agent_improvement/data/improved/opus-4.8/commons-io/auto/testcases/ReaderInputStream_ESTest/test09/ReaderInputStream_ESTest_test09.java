package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test09 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that a ReaderInputStream built from a charset exposes a CharsetEncoder
     * for that charset, and that the encoder reports the charset's maximum bytes-per-char.
     * Here the (mocked) default charset encodes at up to 3.0 bytes per character.
     */
    @Test(timeout = 4000)
    public void encoderReflectsConfiguredCharsetMaxBytesPerChar() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();
        PipedReader sourceReader = new PipedReader(3144);

        ReaderInputStream readerInputStream = new ReaderInputStream(sourceReader, defaultCharset);
        CharsetEncoder encoder = readerInputStream.getCharsetEncoder();

        float EXPECTED_MAX_BYTES_PER_CHAR = 3.0F;
        float TOLERANCE = 0.01F;
        assertEquals(EXPECTED_MAX_BYTES_PER_CHAR, encoder.maxBytesPerChar(), TOLERANCE);
    }
}
