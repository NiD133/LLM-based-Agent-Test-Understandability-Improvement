package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test23 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * A converter built from an empty original-to-encoded map has no encoded
     * letters, so its encoded char length stays at the default minimum of 1.
     */
    @Test(timeout = 4000)
    public void encodedCharLengthOfEmptyMapConverterIsOne() throws Throwable {
        Map<Integer, String> emptyOriginalToEncoded = new HashMap<Integer, String>();

        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(emptyOriginalToEncoded);
        int encodedCharLength = converter.getEncodedCharLength();

        assertEquals(1, encodedCharLength);
    }
}
