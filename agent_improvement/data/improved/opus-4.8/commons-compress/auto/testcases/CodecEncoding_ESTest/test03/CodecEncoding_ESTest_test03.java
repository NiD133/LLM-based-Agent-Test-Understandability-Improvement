package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test03 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies the specifier produced for a {@link RunCodec} whose A and B sub-codecs are
     * both the canonical codec at index 13, with that same codec used as the band default.
     *
     * <p>Because neither sub-codec equals the band default, {@code getSpecifier} encodes:
     * the run header byte (129), the canonical specifier for the A codec (13), and the
     * canonical specifier for the B codec (13). The "12" carries the run length parameter.</p>
     */
    @Test(timeout = 4000)
    public void getSpecifierForRunCodecWithCanonicalSubCodecs() throws Throwable {
        final int canonicalIndex = 13;
        BHSDCodec canonicalCodec = CodecEncoding.getCanonicalCodec(canonicalIndex);

        RunCodec runCodec = new RunCodec(canonicalIndex, canonicalCodec, canonicalCodec);
        BHSDCodec defaultForBand = canonicalCodec;

        int[] specifier = CodecEncoding.getSpecifier(runCodec, defaultForBand);

        assertNotNull(specifier);
        assertArrayEquals(new int[] { 129, 12, 13 }, specifier);
    }
}
