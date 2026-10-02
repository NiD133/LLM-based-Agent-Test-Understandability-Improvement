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
        Character repeatedCharacter = Character.valueOf('T');
        Character[] duplicateOnlyAlphabet = new Character[3];
        duplicateOnlyAlphabet[0] = repeatedCharacter;
        duplicateOnlyAlphabet[1] = repeatedCharacter;
        duplicateOnlyAlphabet[2] = duplicateOnlyAlphabet[0];

        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                duplicateOnlyAlphabet,
                duplicateOnlyAlphabet,
                (Character[]) null);

        assertEquals(1, converter.getEncodedCharLength());
    }
}
