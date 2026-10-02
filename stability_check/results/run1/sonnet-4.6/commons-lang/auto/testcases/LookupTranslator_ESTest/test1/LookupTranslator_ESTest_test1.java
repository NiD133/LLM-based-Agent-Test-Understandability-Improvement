package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test1 extends LookupTranslator_ESTest_scaffolding {

    // Maps "FFFFFCCE" to itself; verifies that translate() returns the mapped value from the lookup table.
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        String inputKey   = "FFFFFCCE";
        String mappedValue = "FFFFFCCE";

        CharSequence[] lookupEntry = new CharSequence[3];
        lookupEntry[0] = inputKey;
        lookupEntry[1] = mappedValue;

        CharSequence[][] lookupTable = new CharSequence[1][7];
        lookupTable[0] = lookupEntry;

        LookupTranslator translator = new LookupTranslator(lookupTable);
        String result = translator.translate((CharSequence) inputKey);

        assertEquals(mappedValue, result);
    }
}
