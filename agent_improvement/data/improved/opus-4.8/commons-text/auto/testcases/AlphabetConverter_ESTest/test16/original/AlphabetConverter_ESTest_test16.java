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
        Character[] characterArray0 = new Character[2];
        Character character0 = Character.valueOf('V');
        characterArray0[0] = character0;
        Character character1 = Character.valueOf('/');
        characterArray0[1] = character1;
        Character[] characterArray1 = new Character[1];
        characterArray1[0] = character0;
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverterFromChars(characterArray0, characterArray0, characterArray1);
        assertEquals(1, alphabetConverter0.getEncodedCharLength());
    }
}
