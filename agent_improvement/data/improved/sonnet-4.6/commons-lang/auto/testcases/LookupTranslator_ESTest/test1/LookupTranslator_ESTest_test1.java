package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test1 extends LookupTranslator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Build a lookup entry that maps "FFFFFCCE" to itself (key at [0], value at [1])
        CharSequence[] lookupEntry = new CharSequence[3];
        lookupEntry[0] = "FFFFFCCE"; // key
        lookupEntry[1] = "FFFFFCCE"; // replacement value (same as key)

        // Wrap the single entry in the outer lookup table
        CharSequence[][] lookupTable = new CharSequence[1][7];
        lookupTable[0] = lookupEntry;

        LookupTranslator translator = new LookupTranslator(lookupTable);

        // Translating the key should return its mapped replacement value
        String result = translator.translate((CharSequence) "FFFFFCCE");
        assertEquals("FFFFFCCE", result);
    }
}
