package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test03 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Build a minimal single-element alphabet using code point 0 (null character)
        Integer nullCharCodePoint = Integer.valueOf(0);
        Integer[] singleCharAlphabet = new Integer[] { nullCharCodePoint };

        // Create a converter where original, encoding, and doNotEncode all use the same alphabet
        AlphabetConverter converter = AlphabetConverter.createConverter(
                singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        // An AlphabetConverter must be reflexively equal to itself
        assertTrue(converter.equals(converter));
    }
}
