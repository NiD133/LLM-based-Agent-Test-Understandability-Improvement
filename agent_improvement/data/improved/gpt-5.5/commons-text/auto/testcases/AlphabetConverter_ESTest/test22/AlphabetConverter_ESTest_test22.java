package org.apache.commons.text;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test22 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];

        AlphabetConverter converter = AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);
        converter.toString();

        assertEquals(1, converter.getEncodedCharLength());
    }
}
