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
public class AlphabetConverter_ESTest_test15 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Character capitalZ = Character.valueOf('Z');
        Character openParenthesis = Character.valueOf('(');
        Character equalsSign = Character.valueOf('=');
        Character lowerCaseU = Character.valueOf('u');

        Character[] originalAlphabet = {
                capitalZ, capitalZ, openParenthesis, openParenthesis,
                equalsSign, capitalZ, openParenthesis, lowerCaseU
        };
        Character[] encodingAlphabet = {
                lowerCaseU, openParenthesis, openParenthesis, capitalZ,
                openParenthesis, lowerCaseU, capitalZ, lowerCaseU
        };
        Character[] doNotEncode = { capitalZ };

        AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                originalAlphabet, encodingAlphabet, doNotEncode);

        assertEquals(2, alphabetConverter.getEncodedCharLength());
    }
}
