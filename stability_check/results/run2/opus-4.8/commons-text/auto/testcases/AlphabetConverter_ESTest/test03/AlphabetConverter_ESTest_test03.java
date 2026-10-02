package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test03 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * A converter should be equal to itself: equals() must be reflexive.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexive() throws Throwable {
        // Build a converter from a single-code-point alphabet (code point 0),
        // reused as the original, encoding, and do-not-encode alphabets.
        Integer[] singleCodePointAlphabet = { Integer.valueOf(0) };
        AlphabetConverter converter = AlphabetConverter.createConverter(
                singleCodePointAlphabet, singleCodePointAlphabet, singleCodePointAlphabet);

        boolean equalToItself = converter.equals(converter);

        assertTrue(equalToItself);
    }
}
