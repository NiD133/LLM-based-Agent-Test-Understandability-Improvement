package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test11 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that the NYSIIS encoder strips out non-letter characters before
     * encoding (via SoundexUtils.clean) and produces the strict, 6-character code
     * derived only from the remaining letters of the input.
     */
    @Test(timeout = 4000)
    public void encodingIgnoresNonLetterCharacters() throws Throwable {
        Nysiis encoder = new Nysiis();

        // The input mixes letters with digits and punctuation; only the letters
        // "BdFLHciJkNw" are considered, yielding the strict NYSIIS code.
        String nysiisCode = encoder.nysiis("Bd}F:LH6ciJkN{]w^1");

        assertNotNull(nysiisCode);
        assertEquals("BDFLCA", nysiisCode);
    }
}
