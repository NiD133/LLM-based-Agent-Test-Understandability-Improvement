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
public class AlphabetConverter_ESTest_test03 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Integer[] sharedAlphabet = new Integer[1];
        Integer zeroCodePoint = new Integer(0);
        sharedAlphabet[0] = zeroCodePoint;

        AlphabetConverter converter = AlphabetConverter.createConverter(sharedAlphabet, sharedAlphabet, sharedAlphabet);
        boolean converterEqualsItself = converter.equals(converter);

        assertTrue(converterEqualsItself);
    }
}
