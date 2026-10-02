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
public class AlphabetConverter_ESTest_test02 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Two converters built from identical (empty) original, encoding and
     * "do not encode" alphabets should be considered equal.
     */
    @Test(timeout = 4000)
    public void twoConvertersBuiltFromEmptyAlphabetsAreEqual() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];

        AlphabetConverter firstConverter =
                AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);
        AlphabetConverter secondConverter =
                AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        boolean convertersAreEqual = secondConverter.equals(firstConverter);

        assertTrue(convertersAreEqual);
    }
}
