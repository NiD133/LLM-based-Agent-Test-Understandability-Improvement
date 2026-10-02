package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test01 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies the specifier produced for a {@link RunCodec} whose A-codec is a
     * {@link PopulationCodec} and whose B-codec equals the band's default codec.
     *
     * <p>The expected specifier {@code [133, 144, 0]} breaks down as:</p>
     * <ul>
     *   <li>133 - the RunCodec header byte (k = 4, B-codec defaulted),</li>
     *   <li>144, 0 - the two-byte specifier for the nested PopulationCodec A-codec.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void specifierForRunCodecWithDefaultedBCodec() throws Throwable {
        BHSDCodec defaultCodec = Codec.DELTA5;

        // A-codec: a population codec built around the default codec.
        PopulationCodec populationCodec = new PopulationCodec(defaultCodec, 4, defaultCodec);
        // RunCodec with k=4, the population codec as A-codec and the default as B-codec.
        RunCodec runCodec = new RunCodec(4, populationCodec, defaultCodec);

        int[] specifier = CodecEncoding.getSpecifier(runCodec, defaultCodec);

        assertNotNull(specifier);
        assertArrayEquals(new int[] { 133, 144, 0 }, specifier);
    }
}
