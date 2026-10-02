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
public class AlphabetConverter_ESTest_test25 extends AlphabetConverter_ESTest_scaffolding {

    private static final int DEFAULT_ENCODED_CHARACTER_LENGTH = 1;

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        HashMap<Integer, String> emptyOriginalToEncoded = new HashMap<Integer, String>();
        AlphabetConverter reconstructedConverter = AlphabetConverter.createConverterFromMap(emptyOriginalToEncoded);

        // Preserve the original EvoSuite coverage call before checking the reconstructed default length.
        reconstructedConverter.hashCode();

        assertEquals(DEFAULT_ENCODED_CHARACTER_LENGTH, reconstructedConverter.getEncodedCharLength());
    }
}
