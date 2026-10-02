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
    private static final String LOOKUP_VALUE = "FFFFFCCE";

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        CharSequence[][] lookupTable = new CharSequence[1][7];
        CharSequence[] lookupEntry = new CharSequence[3];
        lookupEntry[0] = (CharSequence) LOOKUP_KEY;
        lookupEntry[1] = (CharSequence) LOOKUP_VALUE;
        lookupTable[0] = lookupEntry;

        LookupTranslator lookupTranslator = new LookupTranslator(lookupTable);
        String translatedValue = lookupTranslator.translate((CharSequence) LOOKUP_KEY);

        assertEquals(LOOKUP_VALUE, translatedValue);
    }
}
