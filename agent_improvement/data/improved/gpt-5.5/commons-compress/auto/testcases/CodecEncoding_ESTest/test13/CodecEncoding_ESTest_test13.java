package org.apache.commons.compress.harmony.pack200;

import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test13 extends CodecEncoding_ESTest_scaffolding {

    private static final int CANONICAL_CODEC_INDEX = 13;
    private static final int POPULATION_CODEC_ENCODING = 188;
    private static final int RUN_CODEC_ENCODING = 117;
    private static final long CANONICAL_CODEC_LARGEST_VALUE = 4294967293L;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        BHSDCodec defaultCodec = CodecEncoding.getCanonicalCodec(CANONICAL_CODEC_INDEX);
        byte[] bandHeaderBytes = new byte[2];
        ByteArrayInputStream bandHeaderInput = new ByteArrayInputStream(bandHeaderBytes);
        BufferedInputStream bufferedBandHeaderInput = new BufferedInputStream(bandHeaderInput);

        Codec populationCodec = CodecEncoding.getCodec(POPULATION_CODEC_ENCODING, bufferedBandHeaderInput, defaultCodec);
        CodecEncoding.getCodec(RUN_CODEC_ENCODING, bufferedBandHeaderInput, populationCodec.MDELTA5);

        assertEquals(0, bandHeaderInput.available());
        assertEquals(CANONICAL_CODEC_LARGEST_VALUE, defaultCodec.largest());
    }
}
