package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test1 extends LookupTranslator_ESTest_scaffolding {

    /**
     * Verifies that a lookup entry mapping a value to itself translates that
     * value back to itself unchanged.
     */
    @Test(timeout = 4000)
    public void translateReturnsMappedValueWhenInputMatchesLookupKey() throws Throwable {
        final String word = "FFFFFCCE";

        // Build a lookup table whose single entry maps "word" to "word".
        // Each row is [key, replacement]; the trailing null columns are
        // preserved from the original test as they are never read.
        CharSequence[] lookupEntry = new CharSequence[3];
        lookupEntry[0] = word; // key
        lookupEntry[1] = word; // replacement

        CharSequence[][] lookupTable = new CharSequence[1][7];
        lookupTable[0] = lookupEntry;

        LookupTranslator translator = new LookupTranslator(lookupTable);

        String result = translator.translate(word);

        assertEquals(word, result);
    }
}
