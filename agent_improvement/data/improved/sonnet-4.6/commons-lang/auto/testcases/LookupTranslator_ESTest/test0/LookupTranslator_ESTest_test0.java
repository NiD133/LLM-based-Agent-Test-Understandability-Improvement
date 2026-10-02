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
        // Each inner array maps key (index 0) to replacement (index 1).
        // Both entries map "FFFFFFFF" to itself; the extra slots stay null.
        CharSequence[] firstEntry = new CharSequence[4];
        firstEntry[0] = (CharSequence) "FFFFFFFF";
        firstEntry[1] = (CharSequence) "FFFFFFFF";

        CharSequence[] secondEntry = new CharSequence[5];
        secondEntry[0] = (CharSequence) "FFFFFFFF";
        secondEntry[1] = (CharSequence) "FFFFFFFF";
        secondEntry[3] = (CharSequence) "FFFFFCCE";

        // Build the lookup table with two rows; rows are replaced with the arrays above.
        CharSequence[][] lookupTable = new CharSequence[2][6];
        lookupTable[0] = firstEntry;
        lookupTable[1] = secondEntry;

        LookupTranslator translator = new LookupTranslator(lookupTable);

        // "FFFFFCCE" is not a key in the lookup table, so translate returns it unchanged.
        String result = translator.translate(secondEntry[3]);
        assertEquals("FFFFFCCE", result);
    }
}
