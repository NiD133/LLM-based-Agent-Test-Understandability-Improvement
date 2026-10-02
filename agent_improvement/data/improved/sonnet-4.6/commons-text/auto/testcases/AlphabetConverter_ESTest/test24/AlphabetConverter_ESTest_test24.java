package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test24 extends AlphabetConverter_ESTest_scaffolding {

    // When all three alphabets (original, encoding, doNotEncode) are empty,
    // the converter's encoded char length defaults to 1 and the mapping is empty.
    @Test(timeout = 4000)
    public void test_createConverterWithEmptyAlphabets_encodedCharLengthIsOne() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];

        AlphabetConverter converter = AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        converter.getOriginalToEncoded();
        assertEquals(1, converter.getEncodedCharLength());
    }
}
