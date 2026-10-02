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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Use the null character (code point 0) as the sole character in all three alphabet arrays.
        // When original, encoding, and doNotEncode all contain the same single character,
        // the converter maps that character to itself (identity mapping).
        Integer nullCodePoint = Integer.valueOf(0);
        Integer[] singleNullCharAlphabet = new Integer[] { nullCodePoint };

        AlphabetConverter converter = AlphabetConverter.createConverter(
                singleNullCharAlphabet,  // original alphabet
                singleNullCharAlphabet,  // encoding alphabet
                singleNullCharAlphabet   // characters to leave unencoded
        );

        // An AlphabetConverter must be equal to itself (reflexivity of equals).
        boolean isEqualToItself = converter.equals(converter);
        assertTrue(isEqualToItself);
    }
}
