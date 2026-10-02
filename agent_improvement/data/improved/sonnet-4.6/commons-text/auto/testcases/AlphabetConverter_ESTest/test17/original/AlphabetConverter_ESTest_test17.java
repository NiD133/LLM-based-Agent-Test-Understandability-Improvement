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
        Character[] characterArray0 = new Character[8];
        char char0 = 'Z';
        Character character0 = Character.valueOf('Z');
        characterArray0[0] = character0;
        characterArray0[1] = characterArray0[0];
        characterArray0[2] = character0;
        characterArray0[3] = characterArray0[2];
        Character character1 = Character.valueOf('=');
        characterArray0[4] = character1;
        characterArray0[5] = characterArray0[3];
        characterArray0[6] = characterArray0[4];
        characterArray0[7] = character0;
        Character[] characterArray1 = new Character[8];
        characterArray1[0] = characterArray0[5];
        characterArray1[1] = (Character) char0;
        characterArray1[2] = character0;
        characterArray1[3] = characterArray0[2];
        characterArray1[4] = character0;
        characterArray1[5] = characterArray0[0];
        characterArray1[6] = character0;
        characterArray1[7] = characterArray0[2];
        // Undeclared exception!
        try {
            AlphabetConverter.createConverterFromChars(characterArray0, characterArray1, characterArray1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Must have at least two encoding characters (excluding those in the 'do not encode' list), but has 0
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
