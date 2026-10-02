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

    /**
     * Verifies that a converter built from an empty map has a default encoded
     * character length of 1, and that its hashCode() method executes without error.
     *
     * When no mappings are present, no encoded value can exceed length 1, so
     * the converter should report an encoded char length of 1 (the minimum).
     */
    @Test(timeout = 4000)
    public void test25_emptyMapConverterHasDefaultEncodedCharLengthOfOne() throws Throwable {
        HashMap<Integer, String> emptyMapping = new HashMap<Integer, String>();

        AlphabetConverter converterFromEmptyMap = AlphabetConverter.createConverterFromMap(emptyMapping);

        // Confirm hashCode() is callable without throwing an exception
        converterFromEmptyMap.hashCode();

        // An empty mapping has no encoded values, so the encoded char length defaults to 1
        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());
    }
}
