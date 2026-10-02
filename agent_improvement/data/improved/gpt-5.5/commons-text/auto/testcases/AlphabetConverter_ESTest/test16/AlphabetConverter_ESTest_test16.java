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
public class AlphabetConverter_ESTest_test16 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        Character[] sharedOriginalAndEncodingAlphabet = new Character[2];
        Character doNotEncodeCharacter = Character.valueOf('V');
        sharedOriginalAndEncodingAlphabet[0] = doNotEncodeCharacter;
        Character slashCharacter = Character.valueOf('/');
        sharedOriginalAndEncodingAlphabet[1] = slashCharacter;

        Character[] doNotEncodeAlphabet = new Character[1];
        doNotEncodeAlphabet[0] = doNotEncodeCharacter;

        AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                sharedOriginalAndEncodingAlphabet,
                sharedOriginalAndEncodingAlphabet,
                doNotEncodeAlphabet);

        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }
}
