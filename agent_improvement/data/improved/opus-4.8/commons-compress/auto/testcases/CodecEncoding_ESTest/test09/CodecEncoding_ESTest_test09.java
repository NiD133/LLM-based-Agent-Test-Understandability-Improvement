package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * unfavoured sub-codecs are each read from the supplied header stream.
     * Each sub-codec byte is 0, so {@link CodecEncoding#getCodec} reads three
     * bytes from the stream before returning.
     *
     * The header bytes are wrapped in a {@link BufferedInputStream}, which reads
     * the whole underlying buffer ahead in a single gulp. As a result the
     * backing {@link ByteArrayInputStream} reports nothing left to read once
     * getCodec returns.
     */
    @Test(timeout = 4000)
    public void getCodecForPopulationEncodingDrainsBackingStream() throws Throwable {
        BHSDCodec defaultCodec = Codec.CHAR3;

        // Header stream of nine zero bytes; getCodec only needs the first three.
        byte[] headerBytes = new byte[9];
        ByteArrayInputStream backingStream = new ByteArrayInputStream(headerBytes);
        BufferedInputStream headerStream = new BufferedInputStream(backingStream);

        // Value 141 => population codec, reading three sub-codec bytes.
        CodecEncoding.getCodec(141, headerStream, defaultCodec);

        // BufferedInputStream pulled the entire backing buffer into its buffer.
        assertEquals(0, backingStream.available());
    }
}
