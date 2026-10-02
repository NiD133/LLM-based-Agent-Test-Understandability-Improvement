package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test05 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding value 166 falls in the population-codec range (141..188) of
     * {@link CodecEncoding#getCodec(int, java.io.InputStream, Codec)}, so the
     * decoder reads extra header bytes from the supplied stream and builds a
     * composite Codec from the DELTA5 default. Feeding it a two-byte buffer of
     * zeros lets decoding succeed, and we then round-trip the result through
     * {@link CodecEncoding#getSpecifier(Codec, Codec)} to exercise the
     * specifier-generation path. Finally we confirm the underlying byte stream
     * has been fully consumed.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final BHSDCodec defaultCodec = Codec.DELTA5;

        // Header bytes the population-codec decoder will read while resolving
        // the sub-codecs; all zeros so decoding resolves back to the default.
        final byte[] headerBytes = new byte[2];
        final ByteArrayInputStream headerStream = new ByteArrayInputStream(headerBytes);
        final BufferedInputStream bufferedHeaderStream = new BufferedInputStream(headerStream);

        final int populationCodecEncoding = 166;
        final Codec decodedCodec =
                CodecEncoding.getCodec(populationCodecEncoding, bufferedHeaderStream, defaultCodec);

        // Round-trip the decoded codec back into its specifier representation.
        CodecEncoding.getSpecifier(decodedCodec, decodedCodec);

        // Decoding consumed both header bytes, leaving nothing in the stream.
        assertEquals(0, headerStream.available());
    }
}
