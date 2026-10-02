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
public class CodecEncoding_ESTest_test09 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding value 141 selects a population codec whose favoured, token and
     * unfavoured sub-codecs are each read from the supplied header stream. With
     * a stream of zero bytes, {@link CodecEncoding#getCodec} resolves those
     * sub-codecs and drains the header stream in the process.
     *
     * <p>The header bytes are wrapped in a {@link BufferedInputStream}, which
     * eagerly buffers the entire underlying stream on the first read, so the
     * backing {@link ByteArrayInputStream} reports no bytes remaining afterward.
     */
    @Test(timeout = 4000)
    public void getCodecForPopulationEncodingDrainsHeaderStream() throws Throwable {
        BHSDCodec defaultCodec = Codec.CHAR3;
        byte[] headerBytes = new byte[9];
        ByteArrayInputStream backingStream = new ByteArrayInputStream(headerBytes);
        BufferedInputStream headerStream = new BufferedInputStream(backingStream);

        int populationEncodingValue = 141;
        CodecEncoding.getCodec(populationEncodingValue, headerStream, defaultCodec);

        assertEquals(0, backingStream.available());
    }
}
