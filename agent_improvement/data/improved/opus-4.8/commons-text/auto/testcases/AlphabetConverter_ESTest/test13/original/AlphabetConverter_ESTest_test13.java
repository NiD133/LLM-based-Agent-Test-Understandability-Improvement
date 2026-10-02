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
        Character[] characterArray0 = new Character[8];
        char char0 = 'T';
        Character character0 = Character.valueOf('T');
        characterArray0[0] = character0;
        characterArray0[1] = character0;
        characterArray0[2] = character0;
        Character character1 = Character.valueOf('q');
        characterArray0[3] = character1;
        Character character2 = Character.valueOf('=');
        characterArray0[4] = character2;
        characterArray0[5] = characterArray0[2];
        characterArray0[6] = characterArray0[0];
        char char1 = 'u';
        Character character3 = Character.valueOf('u');
        characterArray0[7] = character3;
        Character[] characterArray1 = new Character[8];
        characterArray1[0] = character3;
        characterArray1[1] = (Character) char1;
        characterArray1[2] = character1;
        characterArray1[3] = (Character) char0;
        characterArray1[4] = character0;
        characterArray1[5] = character3;
        characterArray1[6] = character0;
        characterArray1[7] = character3;
        Character[] characterArray2 = new Character[1];
        characterArray2[0] = (Character) char1;
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverterFromChars(characterArray0, characterArray1, characterArray2);
        assertEquals(2, alphabetConverter0.getEncodedCharLength());
    }
}
