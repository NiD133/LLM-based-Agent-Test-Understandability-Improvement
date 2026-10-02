package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test02 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies the specifier encoding for a non-default {@link RunCodec}.
     *
     * <p>The codec under test has the maximum possible run length
     * ({@link Integer#MAX_VALUE}) and uses {@link Codec#BYTE1} for both its A
     * and B sub-codecs. Because neither sub-codec matches the supplied
     * default-for-band codec (the RunCodec itself), both must be spelled out in
     * the resulting specifier.</p>
     *
     * <p>The expected specifier {@code { 124, 524286, 1, 1 }} breaks down as:</p>
     * <ul>
     *   <li>{@code 124} - the leading RunCodec byte (117 base, encoding kb=3
     *       since K &gt; 65536, plus the kx-present flag).</li>
     *   <li>{@code 524286} - the encoded kx value derived from the run length.</li>
     *   <li>{@code 1} - the canonical specifier for the A sub-codec (BYTE1).</li>
     *   <li>{@code 1} - the canonical specifier for the B sub-codec (BYTE1).</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void specifierForMaxLengthRunCodecOfByte1SubCodecs() throws Throwable {
        BHSDCodec byte1Codec = Codec.BYTE1;
        RunCodec runCodec = new RunCodec(Integer.MAX_VALUE, byte1Codec, byte1Codec);

        // The RunCodec is also passed as the default-for-band so that its A/B
        // sub-codecs (BYTE1) are treated as non-default and encoded explicitly.
        int[] specifier = CodecEncoding.getSpecifier(runCodec, runCodec);

        int[] expectedSpecifier = { 124, 524286, 1, 1 };
        assertArrayEquals(expectedSpecifier, specifier);
    }
}
