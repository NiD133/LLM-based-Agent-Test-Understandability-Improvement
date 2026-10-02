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
public class AlphabetConverter_ESTest_test07 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * An AlphabetConverter created from an empty mapping has no character
     * substitutions defined. Encoding an empty input string should succeed
     * (nothing to substitute), and the default encoded character length
     * should be 1 because no multi-character encoding was ever registered.
     */
    @Test(timeout = 4000)
    public void test_encodeEmptyString_withEmptyMapping_defaultEncodedLengthIsOne() throws Throwable {
        // Build a converter that has no substitution rules at all
        HashMap<Integer, String> emptyMapping = new HashMap<Integer, String>();
        AlphabetConverter converterWithNoRules = AlphabetConverter.createConverterFromMap(emptyMapping);

        // Encoding an empty string should not throw even though the mapping is empty
        converterWithNoRules.encode("");

        // With no mappings the encoded-char length stays at the initial default of 1
        assertEquals(1, converterWithNoRules.getEncodedCharLength());
    }
}
