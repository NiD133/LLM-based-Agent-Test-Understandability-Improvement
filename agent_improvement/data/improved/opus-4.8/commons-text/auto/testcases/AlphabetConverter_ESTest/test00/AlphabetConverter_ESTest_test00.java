package org.apache.commons.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test00 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Two converters built from maps with different contents (one empty, one
     * containing a single mapping) should not be considered equal, even though
     * both end up with the default encoded-char length of 1.
     */
    @Test(timeout = 4000)
    public void convertersBuiltFromDifferentMapsAreNotEqual() throws Throwable {
        // Build a converter from an empty mapping.
        Map<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        AlphabetConverter converterFromEmptyMap =
                AlphabetConverter.createConverterFromMap(originalToEncoded);

        // Add one mapping and build a second converter from the now non-empty map.
        originalToEncoded.put(Integer.valueOf(-1147692044), "A");
        AlphabetConverter converterFromOneEntryMap =
                AlphabetConverter.createConverterFromMap(originalToEncoded);

        // The two converters hold different mappings, so they are not equal.
        boolean convertersEqual = converterFromOneEntryMap.equals(converterFromEmptyMap);
        assertFalse(convertersEqual);

        // Single-character encodings keep the default encoded-char length of 1.
        assertEquals(1, converterFromOneEntryMap.getEncodedCharLength());
        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());
    }
}
