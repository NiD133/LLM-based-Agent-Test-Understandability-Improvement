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
     * A custom mapping string that lacks entries for standard letters like 'N'.
     * When Soundex tries to map 'N' from the input, it cannot find a valid code
     * and throws IllegalArgumentException.
     */
    private static final String INCOMPLETE_MAPPING = "'NY8Wa^[4";

    @Test(timeout = 4000)
    public void test_encodeWithIncompleteMapping_throwsIllegalArgumentExceptionForUnmappedCharacter() throws Throwable {
        // Build a Soundex instance with a non-standard mapping that does not cover all 26 letters.
        // The mapping "'NY8Wa^[4" only covers 9 positions (indices 0–8), so any letter whose
        // index (ch - 'A') falls outside that range is unmapped.
        Soundex soundexWithIncompleteMapping = new Soundex(INCOMPLETE_MAPPING);

        // Encoding the same string used as the mapping exposes an unmapped letter.
        // After cleaning, the first alphabetic character encountered that lies beyond
        // the mapping's length causes Soundex.map() to throw IllegalArgumentException
        // with the message "The character is not mapped: N (index=13)".
        try {
            soundexWithIncompleteMapping.encode(INCOMPLETE_MAPPING);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.codec.language.Soundex", e);
        }
    }
}
