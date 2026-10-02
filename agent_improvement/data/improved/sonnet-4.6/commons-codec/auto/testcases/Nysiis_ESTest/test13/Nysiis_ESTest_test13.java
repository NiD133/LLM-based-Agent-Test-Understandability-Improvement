package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test13 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that NYSIIS encodes a mixed-content string (special characters, digits, spaces,
     * and letters) by stripping non-alphabetic characters and applying phonetic rules to the
     * remaining letters, producing a 6-character strict-mode result.
     *
     * Input "@A9Q mhK(EVj%r" strips to "AQMHKEVJR", then NYSIIS rules collapse it to "AGNCAF".
     */
    @Test(timeout = 4000)
    public void test13_encodeMixedSpecialCharsAndLetters_returnsNysiisCode() throws Throwable {
        Nysiis nysiis = new Nysiis();
        String input = "@A9Q mhK(EVj%r";
        String expectedEncoding = "AGNCAF";

        String actualEncoding = nysiis.encode(input);

        assertEquals(expectedEncoding, actualEncoding);
    }
}
