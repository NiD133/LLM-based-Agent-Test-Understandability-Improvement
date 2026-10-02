package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test01 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Two converters built from different alphabets should not be considered equal.
     * One is built from empty integer alphabets, the other from single-character
     * alphabets, so their internal mappings differ.
     */
    @Test(timeout = 4000)
    public void equals_returnsFalse_forConvertersWithDifferentAlphabets() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];
        AlphabetConverter emptyConverter =
                AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        Character[] singleCharAlphabet = { Character.valueOf('3') };
        AlphabetConverter singleCharConverter = AlphabetConverter.createConverterFromChars(
                singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        boolean areEqual = singleCharConverter.equals(emptyConverter);

        assertFalse(areEqual);
    }
}
