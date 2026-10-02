package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test01 extends CodecEncoding_ESTest_scaffolding {

    private static final int RUN_LENGTH = 4;
    private static final int POPULATION_TOKEN_L = 4;
    private static final int[] EXPECTED_RUN_CODEC_SPECIFIER = { 133, 144, 0 };

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        final BHSDCodec defaultCodec = Codec.DELTA5;
        final PopulationCodec populationCodec = new PopulationCodec(defaultCodec, POPULATION_TOKEN_L, defaultCodec);
        final RunCodec runCodec = new RunCodec(RUN_LENGTH, populationCodec, defaultCodec);

        final int[] specifier = CodecEncoding.getSpecifier(runCodec, defaultCodec);

        assertNotNull(specifier);
        assertArrayEquals(EXPECTED_RUN_CODEC_SPECIFIER, specifier);
    }
}
