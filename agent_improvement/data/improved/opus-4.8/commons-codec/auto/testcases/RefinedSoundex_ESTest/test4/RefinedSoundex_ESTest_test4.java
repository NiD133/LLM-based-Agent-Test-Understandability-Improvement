package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test4 extends RefinedSoundex_ESTest_scaffolding {

    /**
     * Verifies that {@link RefinedSoundex#difference(String, String)} reports a
     * difference of 1 when the same string is compared against itself using a
     * custom (all-zero) mapping.
     *
     * <p>With a mapping of six '\0' characters, every letter resolves to the
     * code 0, so each encoded string collapses to just its first character.
     * Comparing two identical encodings therefore yields a single matching
     * character.</p>
     */
    @Test(timeout = 4000)
    public void difference_ofIdenticalStringsWithAllZeroMapping_returnsOne() throws Throwable {
        char[] allZeroMapping = new char[6];
        RefinedSoundex refinedSoundex = new RefinedSoundex(allZeroMapping);

        String input = "org.apache.commons.codec.EncoderException";
        int difference = refinedSoundex.difference(input, input);

        assertEquals(1, difference);
    }
}
