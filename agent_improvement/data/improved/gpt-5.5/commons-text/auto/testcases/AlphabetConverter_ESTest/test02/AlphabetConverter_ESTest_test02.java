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
public class AlphabetConverter_ESTest_test02 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];

        AlphabetConverter firstEmptyConverter =
                AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);
        AlphabetConverter secondEmptyConverter =
                AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        boolean convertersAreEqual = secondEmptyConverter.equals(firstEmptyConverter);

        assertTrue(convertersAreEqual);
    }
}
