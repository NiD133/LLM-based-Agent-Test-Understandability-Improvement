package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test01 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link CharSetUtils#squeeze(String, String...)} collapses runs of
     * repeated characters when those characters belong to the supplied set.
     *
     * <p>The character set contains the letter 'f'. In the input, only the word
     * "offset" has a repeated character in the set ("ff"), so that run is squeezed to a
     * single 'f' ("ofset"). All other characters are left untouched.</p>
     */
    @Test(timeout = 4000)
    public void squeezeCollapsesRepeatedCharactersThatAreInTheSet() throws Throwable {
        String input = "Minimum abbreviation width with offset is %d";

        // Set-syntax characters to squeeze; includes 'f', the only repeated char in the input.
        // The array has trailing null entries, which CharSetUtils ignores.
        String[] charactersToSqueeze = new String[4];
        charactersToSqueeze[1] = "ZS[4!;6>G|3UPaJfj";

        String squeezed = CharSetUtils.squeeze(input, charactersToSqueeze);

        // "offset" becomes "ofset"; everything else is unchanged.
        assertEquals("Minimum abbreviation width with ofset is %d", squeezed);
    }
}
