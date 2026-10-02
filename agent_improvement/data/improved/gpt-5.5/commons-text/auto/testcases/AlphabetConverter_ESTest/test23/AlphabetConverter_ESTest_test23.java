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
public class AlphabetConverter_ESTest_test23 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        HashMap<Integer, String> emptyOriginalToEncoded = new HashMap<Integer, String>();
        AlphabetConverter converterFromEmptyMap = AlphabetConverter.createConverterFromMap(emptyOriginalToEncoded);
        int encodedCharLength = converterFromEmptyMap.getEncodedCharLength();
        assertEquals(1, encodedCharLength);
    }
}
