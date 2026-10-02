package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test15 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Decodes a "run codec" (encoding value 128 lies in the 117..140 run-codec
     * range) and then round-trips it back through {@link CodecEncoding#getSpecifier},
     * verifying that the codec is rebuilt into its expected specifier byte sequence.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Canonical codec at index 13 is used as the default codec for decoding.
        final BHSDCodec defaultCodec = CodecEncoding.getCanonicalCodec(13);

        // A single 0x00 byte supplies the extra header byte that a run codec reads.
        final byte[] bandHeaders = new byte[1];
        final ByteArrayInputStream headerStream = new ByteArrayInputStream(bandHeaders);

        // Encoding value 128 selects a run codec, consuming the header byte.
        final Codec runCodec = CodecEncoding.getCodec(128, headerStream, defaultCodec);

        final int[] specifier = CodecEncoding.getSpecifier(runCodec, Codec.UNSIGNED5);

        // The whole header stream should have been consumed during decoding.
        assertEquals(0, headerStream.available());
        // Expected specifier bytes for the decoded run codec.
        assertArrayEquals(new int[] { 123, 63, 13, 13 }, specifier);
    }
}
