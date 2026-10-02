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
public class AlphabetConverter_ESTest_test17 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Character[] originalAlphabet = new Character[8];
        char repeatedOriginalCharacter = 'Z';
        Character zCharacter = Character.valueOf('Z');
        originalAlphabet[0] = zCharacter;
        originalAlphabet[1] = originalAlphabet[0];
        originalAlphabet[2] = zCharacter;
        originalAlphabet[3] = originalAlphabet[2];
        Character equalsCharacter = Character.valueOf('=');
        originalAlphabet[4] = equalsCharacter;
        originalAlphabet[5] = originalAlphabet[3];
        originalAlphabet[6] = originalAlphabet[4];
        originalAlphabet[7] = zCharacter;

        Character[] encodingAndDoNotEncodeAlphabet = new Character[8];
        encodingAndDoNotEncodeAlphabet[0] = originalAlphabet[5];
        encodingAndDoNotEncodeAlphabet[1] = (Character) repeatedOriginalCharacter;
        encodingAndDoNotEncodeAlphabet[2] = zCharacter;
        encodingAndDoNotEncodeAlphabet[3] = originalAlphabet[2];
        encodingAndDoNotEncodeAlphabet[4] = zCharacter;
        encodingAndDoNotEncodeAlphabet[5] = originalAlphabet[0];
        encodingAndDoNotEncodeAlphabet[6] = zCharacter;
        encodingAndDoNotEncodeAlphabet[7] = originalAlphabet[2];

        try {
            AlphabetConverter.createConverterFromChars(
                    originalAlphabet,
                    encodingAndDoNotEncodeAlphabet,
                    encodingAndDoNotEncodeAlphabet);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
