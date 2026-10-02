package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test22 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the original, encoding and "do not encode" alphabets are all empty,
     * the converter is built with the default encoded-char length of 1, and
     * its toString() can be called without error.
     */
    @Test(timeout = 4000)
    public void createConverterWithEmptyAlphabetsUsesEncodedCharLengthOne() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];

        AlphabetConverter converter =
                AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        converter.toString();
        assertEquals(1, converter.getEncodedCharLength());
    }
}
