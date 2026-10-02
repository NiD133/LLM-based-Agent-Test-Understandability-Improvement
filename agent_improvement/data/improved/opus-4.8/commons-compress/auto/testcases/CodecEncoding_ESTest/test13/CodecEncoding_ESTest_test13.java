package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test13 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Decodes two encoding specifiers (188 = a population codec, then 117 = a run codec)
     * from the same header stream, then verifies that the canonical codec at index 13 is
     * the (4,256) codec whose largest representable value is 2^32 - 3.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Canonical codec #13 is BHSDCodec(4, 256): a 4-byte, base-256 codec.
        BHSDCodec canonicalCodec13 = CodecEncoding.getCanonicalCodec(13);

        // Header stream supplying the extra bytes the encodings read. Two zero bytes
        // are enough to satisfy both getCodec calls below.
        byte[] headerBytes = new byte[2];
        ByteArrayInputStream headerSource = new ByteArrayInputStream(headerBytes);
        BufferedInputStream headerStream = new BufferedInputStream(headerSource);

        // Value 188 selects a population codec, consuming bytes from the header stream.
        Codec populationCodec = CodecEncoding.getCodec(188, headerStream, canonicalCodec13);

        // Value 117 selects a run codec, using MDELTA5 as the default codec.
        CodecEncoding.getCodec(117, headerStream, Codec.MDELTA5);

        // Both header bytes were consumed, so nothing remains in the underlying source.
        assertEquals(0, headerSource.available());
        // BHSDCodec(4, 256) can represent values up to 2^32 - 3.
        assertEquals(4294967293L, canonicalCodec13.largest());
    }
}
