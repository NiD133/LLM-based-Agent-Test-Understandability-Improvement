package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test0 extends LookupTranslator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        CharSequence[][] lookupTable = new CharSequence[2][6];

        CharSequence[] firstEntry = new CharSequence[4];
        firstEntry[0] = (CharSequence) "FFFFFFFF";
        firstEntry[1] = (CharSequence) "FFFFFFFF";
        lookupTable[0] = firstEntry;

        CharSequence[] secondEntry = new CharSequence[5];
        secondEntry[0] = (CharSequence) "FFFFFFFF";
        secondEntry[1] = (CharSequence) "FFFFFFFF";
        secondEntry[3] = (CharSequence) "FFFFFCCE";
        lookupTable[1] = secondEntry;

        LookupTranslator lookupTranslator = new LookupTranslator(lookupTable);
        String translatedValue = lookupTranslator.translate(secondEntry[3]);

        assertEquals("FFFFFCCE", translatedValue);
    }
}
