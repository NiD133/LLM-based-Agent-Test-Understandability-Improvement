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
public class CodecEncoding_ESTest_test01 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Use DELTA5 as the shared default-for-band codec and as both sub-codecs of the PopulationCodec
        BHSDCodec delta5Codec = Codec.DELTA5;

        // PopulationCodec with DELTA5 as both favoured and unfavoured sub-codecs, l=4
        PopulationCodec populationCodec = new PopulationCodec(delta5Codec, 4, delta5Codec);

        // RunCodec: first 4 values encoded with populationCodec (A-codec), rest with delta5Codec (B-codec)
        RunCodec runCodec = new RunCodec(4, populationCodec, delta5Codec);

        int[] specifier = CodecEncoding.getSpecifier(runCodec, delta5Codec);

        assertNotNull(specifier);
        // [133] = RunCodec marker (117) with kx=3 and bdef=1 flags
        // [144] = PopulationCodec marker (141) with fDef=1 and uDef=1 flags
        // [0]   = token codec specifier byte
        assertArrayEquals(new int[] {133, 144, 0}, specifier);
    }
}
