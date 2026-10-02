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
public class AlphabetConverter_ESTest_test01 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];
        AlphabetConverter emptyConverter = AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        Character digitThree = Character.valueOf('3');
        Character[] digitThreeAlphabet = new Character[1];
        digitThreeAlphabet[0] = digitThree;
        AlphabetConverter digitThreeConverter = AlphabetConverter.createConverterFromChars(
                digitThreeAlphabet,
                digitThreeAlphabet,
                digitThreeAlphabet);

        boolean convertersAreEqual = digitThreeConverter.equals(emptyConverter);
        assertFalse(convertersAreEqual);
    }
}
