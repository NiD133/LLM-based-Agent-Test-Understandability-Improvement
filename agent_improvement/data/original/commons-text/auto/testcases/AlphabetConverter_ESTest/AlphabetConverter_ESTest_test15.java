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
        Character[] characterArray0 = new Character[8];
        Character character0 = Character.valueOf('Z');
        characterArray0[0] = character0;
        characterArray0[1] = character0;
        Character character1 = Character.valueOf('(');
        characterArray0[2] = character1;
        characterArray0[3] = characterArray0[2];
        Character character2 = Character.valueOf('=');
        characterArray0[4] = character2;
        characterArray0[5] = character0;
        characterArray0[6] = character1;
        Character character3 = Character.valueOf('u');
        characterArray0[7] = character3;
        Character[] characterArray1 = new Character[8];
        characterArray1[0] = character3;
        characterArray1[1] = characterArray0[3];
        characterArray1[2] = character1;
        characterArray1[3] = characterArray0[0];
        characterArray1[4] = character1;
        characterArray1[5] = character3;
        characterArray1[6] = character0;
        characterArray1[7] = character3;
        Character[] characterArray2 = new Character[1];
        characterArray2[0] = character0;
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverterFromChars(characterArray0, characterArray1, characterArray2);
        assertEquals(2, alphabetConverter0.getEncodedCharLength());
    }
}
