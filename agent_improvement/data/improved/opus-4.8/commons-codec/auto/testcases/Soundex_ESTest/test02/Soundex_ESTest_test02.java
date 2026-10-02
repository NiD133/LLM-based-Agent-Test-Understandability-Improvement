package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test02 extends Soundex_ESTest_scaffolding {

    /**
     * Encoding fails when the input contains a letter whose alphabet position
     * falls outside the custom mapping.
     *
     * The mapping "'NY8Wa^[4" is only 9 characters long, so it only covers the
     * alphabet positions 0..8 (letters A..I). After cleaning, the first letter
     * to encode is 'N', whose position ('N' - 'A' = 13) is past the end of the
     * mapping, so Soundex reports the character as unmapped.
     */
    @Test(timeout = 4000)
    public void encodeWithLetterBeyondMappingThrowsIllegalArgument() throws Throwable {
        String shortMapping = "'NY8Wa^[4";
        Soundex soundex = new Soundex(shortMapping);

        try {
            soundex.encode(shortMapping);
            fail("Expected an IllegalArgumentException because letter N is not mapped");
        } catch (IllegalArgumentException e) {
            // Message: "The character is not mapped: N (index=13)"
            verifyException("org.apache.commons.codec.language.Soundex", e);
        }
    }
}
