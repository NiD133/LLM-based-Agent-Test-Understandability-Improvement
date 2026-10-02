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
public class AlphabetConverter_ESTest_test13 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Character repeatedOriginal = Character.valueOf('T');
        Character encodedLetter = Character.valueOf('u');
        Character alternateOriginal = Character.valueOf('q');
        Character equalsSignOriginal = Character.valueOf('=');

        Character[] originalAlphabet = new Character[] {
            repeatedOriginal,
            repeatedOriginal,
            repeatedOriginal,
            alternateOriginal,
            equalsSignOriginal,
            repeatedOriginal,
            repeatedOriginal,
            encodedLetter
        };

        Character[] encodingAlphabet = new Character[] {
            encodedLetter,
            encodedLetter,
            alternateOriginal,
            repeatedOriginal,
            repeatedOriginal,
            encodedLetter,
            repeatedOriginal,
            encodedLetter
        };

        Character[] doNotEncode = new Character[] {
            encodedLetter
        };

        AlphabetConverter alphabetConverter =
                AlphabetConverter.createConverterFromChars(originalAlphabet, encodingAlphabet, doNotEncode);

        assertEquals(2, alphabetConverter.getEncodedCharLength());
    }
}
