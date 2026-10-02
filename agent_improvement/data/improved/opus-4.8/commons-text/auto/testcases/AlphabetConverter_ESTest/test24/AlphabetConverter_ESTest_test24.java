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
public class AlphabetConverter_ESTest_test24 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Building a converter from empty original, encoding and 'do not encode'
     * alphabets yields a converter whose encoded character length defaults to 1.
     */
    @Test(timeout = 4000)
    public void createConverterFromEmptyAlphabetsHasEncodedCharLengthOne() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];

        AlphabetConverter converter =
                AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        converter.getOriginalToEncoded();

        assertEquals(1, converter.getEncodedCharLength());
    }
}
