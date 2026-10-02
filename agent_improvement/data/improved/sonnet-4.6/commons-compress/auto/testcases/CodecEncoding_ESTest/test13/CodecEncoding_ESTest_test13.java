package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test13 extends CodecEncoding_ESTest_scaffolding {

    // Verifies that a PopulationCodec (value 188) with all-default sub-codecs consumes no stream bytes,
    // while a RunCodec (value 117) reads two bytes to resolve its A and B sub-codecs (both encoded as 0 = default).
    // Also checks that canonical codec 13 (BHSDCodec(4, 256)) reports the expected largest value.
    @Test(timeout = 4000)
    public void testGetCodecPopulationAndRunCodecStreamConsumption() throws Throwable {
        BHSDCodec canonicalCodec13 = CodecEncoding.getCanonicalCodec(13);

        // Two zero bytes: will be consumed by getCodec(117) to resolve its A and B sub-codecs
        byte[] inputBytes = new byte[2];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputBytes);
        BufferedInputStream bufferedStream = new BufferedInputStream(byteArrayInputStream);

        // Value 188 is the maximum PopulationCodec specifier; both f and u default to canonicalCodec13,
        // so no bytes are read from bufferedStream
        Codec populationCodec = CodecEncoding.getCodec(188, bufferedStream, canonicalCodec13);

        // Value 117 is the minimum RunCodec specifier; it reads two bytes (both 0) for A and B sub-codecs
        CodecEncoding.getCodec(117, bufferedStream, populationCodec.MDELTA5);

        // Both bytes in the underlying stream should now be consumed
        assertEquals(0, byteArrayInputStream.available());

        // BHSDCodec(4, 256) has a well-defined largest representable value
        assertEquals(4294967293L, canonicalCodec13.largest());
    }
}
