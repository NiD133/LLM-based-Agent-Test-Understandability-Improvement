package org.apache.commons.text;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test07 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * A converter built from an empty original-to-encoded map should default to an
     * encoded character length of 1, and encoding the empty string should succeed.
     */
    @Test(timeout = 4000)
    public void encodeEmptyStringWithEmptyMapConverterKeepsDefaultCharLength() throws Throwable {
        Map<Integer, String> emptyOriginalToEncoded = new HashMap<Integer, String>();

        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(emptyOriginalToEncoded);
        converter.encode("");

        assertEquals(1, converter.getEncodedCharLength());
    }
}
