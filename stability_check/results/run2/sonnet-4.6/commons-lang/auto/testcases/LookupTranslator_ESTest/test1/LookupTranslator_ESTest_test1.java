package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test1 extends LookupTranslator_ESTest_scaffolding {

    private static final String LOOKUP_KEY = "FFFFFCCE";

    @Test(timeout = 4000)
    public void test_translateReturnsValueForMatchingKey() throws Throwable {
        // Build a single-entry lookup table: LOOKUP_KEY -> LOOKUP_KEY (identity mapping)
        CharSequence[] entry = new CharSequence[3];
        entry[0] = (CharSequence) LOOKUP_KEY; // key to match
        entry[1] = (CharSequence) LOOKUP_KEY; // value to return on match

        CharSequence[][] lookupTable = new CharSequence[1][7];
        lookupTable[0] = entry;

        LookupTranslator translator = new LookupTranslator(lookupTable);
        String result = translator.translate((CharSequence) LOOKUP_KEY);

        assertEquals(LOOKUP_KEY, result);
    }
}
