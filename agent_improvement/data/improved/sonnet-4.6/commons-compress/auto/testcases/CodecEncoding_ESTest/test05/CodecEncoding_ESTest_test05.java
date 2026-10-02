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
public class CodecEncoding_ESTest_test05 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that getCodec with value 166 (a PopulationCodec specifier) reads the expected
     * bytes from the input stream, and that getSpecifier can round-trip the resulting codec.
     *
     * Value 166 falls in the range 141-188, which encodes a PopulationCodec. With offset=25
     * (166-141): fdef=true (uses default codec for favoured), udef=false (reads uCodec from
     * stream), tdef=true (uses a canonical token codec). The BufferedInputStream eagerly reads
     * all bytes from the underlying ByteArrayInputStream, leaving it fully consumed.
     */
    @Test(timeout = 4000)
    public void test_getCodec_populationCodecSpecifier_consumesAllStreamBytes() throws Throwable {
        BHSDCodec defaultCodec = Codec.DELTA5;

        // Two zero bytes supply the uCodec sub-specifier (value 0 → default codec)
        byte[] headerBytes = new byte[2];
        ByteArrayInputStream byteSource = new ByteArrayInputStream(headerBytes);
        // BufferedInputStream buffers all available bytes on the first read,
        // so byteSource will report 0 bytes available after the codec is decoded
        BufferedInputStream bandHeaders = new BufferedInputStream(byteSource);

        // 166 encodes a PopulationCodec: favoured=defaultCodec, tokenL=128, unfavoured from stream
        Codec populationCodec = CodecEncoding.getCodec(166, bandHeaders, defaultCodec);

        // Verify the codec can be described as a specifier (round-trip sanity check)
        CodecEncoding.getSpecifier(populationCodec, populationCodec);

        // BufferedInputStream eagerly drained all bytes from the underlying source
        assertEquals(0, byteSource.available());
    }
}
