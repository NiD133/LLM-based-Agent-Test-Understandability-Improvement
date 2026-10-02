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
public class AlphabetConverter_ESTest_test01 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Create a converter backed by an empty alphabet (no source, encoding, or pass-through chars)
        Integer[] emptyAlphabet = new Integer[0];
        AlphabetConverter emptyConverter = AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        // Create a converter backed by the single character '3' for all three alphabet roles
        Character digitThree = Character.valueOf('3');
        Character[] singleCharAlphabet = new Character[]{digitThree};
        AlphabetConverter singleCharConverter = AlphabetConverter.createConverterFromChars(singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        // A converter with a non-empty alphabet must not equal one built from an empty alphabet
        boolean convertersAreEqual = singleCharConverter.equals(emptyConverter);
        assertFalse(convertersAreEqual);
    }
}
