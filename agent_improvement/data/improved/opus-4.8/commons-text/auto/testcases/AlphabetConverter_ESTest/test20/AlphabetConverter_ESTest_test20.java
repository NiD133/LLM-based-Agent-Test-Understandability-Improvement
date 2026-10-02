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
public class AlphabetConverter_ESTest_test20 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the original and encoding alphabets each collapse to a single distinct
     * character (here three references to 'T'), the converter only needs one
     * encoded character per original character. Verifies the encoded-char length is 1.
     */
    @Test(timeout = 4000)
    public void createConverterFromSingleRepeatedCharProducesEncodedLengthOfOne() throws Throwable {
        Character repeatedChar = Character.valueOf('T');
        Character[] alphabetOfRepeatedChar = { repeatedChar, repeatedChar, repeatedChar };

        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                alphabetOfRepeatedChar, alphabetOfRepeatedChar, (Character[]) null);

        assertEquals(1, converter.getEncodedCharLength());
    }
}
