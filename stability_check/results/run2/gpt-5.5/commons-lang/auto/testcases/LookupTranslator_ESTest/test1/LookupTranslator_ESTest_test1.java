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
        CharSequence[][] lookupTable = new CharSequence[1][7];
        CharSequence[] lookupEntry = new CharSequence[3];
        lookupEntry[0] = (CharSequence) "FFFFFCCE";
        lookupEntry[1] = (CharSequence) "FFFFFCCE";
        lookupTable[0] = lookupEntry;

        LookupTranslator translator = new LookupTranslator(lookupTable);
        String translatedText = translator.translate((CharSequence) "FFFFFCCE");

        assertEquals("FFFFFCCE", translatedText);
    }
}
