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

    /**
     * Two converters built from identical empty alphabets must be equal.
     * Empty arrays mean no source characters, no encoding characters, and
     * nothing to leave unencoded — so both converters produce the same
     * (trivially empty) internal mapping, making equals() return true.
     */
    @Test(timeout = 4000)
    public void test_convertersWithIdenticalEmptyAlphabetsAreEqual() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];

        AlphabetConverter firstConverter  = AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);
        AlphabetConverter secondConverter = AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        assertTrue(secondConverter.equals(firstConverter));
    }
}
