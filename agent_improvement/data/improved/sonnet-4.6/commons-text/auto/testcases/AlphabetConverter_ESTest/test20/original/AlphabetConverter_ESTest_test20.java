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

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        Character[] characterArray0 = new Character[3];
        Character character0 = Character.valueOf('T');
        characterArray0[0] = character0;
        characterArray0[1] = character0;
        characterArray0[2] = characterArray0[0];
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverterFromChars(characterArray0, characterArray0, (Character[]) null);
        assertEquals(1, alphabetConverter0.getEncodedCharLength());
    }
}
