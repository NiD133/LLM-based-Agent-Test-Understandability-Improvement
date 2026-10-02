package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test12 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that {@link Nysiis#nysiis(String)} ignores non-letter characters
     * and encodes only the alphabetic content of the input.
     *
     * <p>The raw input mixes letters with digits and punctuation. The encoder first
     * cleans the string down to its letters ("TLCOHAEUUAJ") and then applies the
     * NYSIIS algorithm, yielding the 6-character code "TLCAHA".</p>
     */
    @Test(timeout = 4000)
    public void encodingInputWithDigitsAndPunctuationKeepsOnlyLetters() throws Throwable {
        Nysiis nysiis = new Nysiis();

        String encoded = nysiis.nysiis("tl1[CoH5>Aeu)UA;J.");

        assertNotNull(encoded);
        assertEquals("TLCAHA", encoded);
    }
}
