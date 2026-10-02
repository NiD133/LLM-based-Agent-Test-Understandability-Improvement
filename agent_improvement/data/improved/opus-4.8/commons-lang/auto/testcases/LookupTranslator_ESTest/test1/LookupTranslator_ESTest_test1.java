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
     * When the lookup table maps a word to itself, translating that exact
     * word returns the same word unchanged.
     */
    @Test(timeout = 4000)
    public void translateReturnsMappedValueForExactMatch() throws Throwable {
        final String word = "FFFFFCCE";

        // Single lookup entry: "FFFFFCCE" -> "FFFFFCCE".
        // The constructor only reads index 0 (key) and index 1 (value) of each row.
        CharSequence[] mapping = new CharSequence[3];
        mapping[0] = word;
        mapping[1] = word;
        CharSequence[][] lookupTable = new CharSequence[1][7];
        lookupTable[0] = mapping;

        LookupTranslator lookupTranslator = new LookupTranslator(lookupTable);

        String translated = lookupTranslator.translate((CharSequence) word);

        assertEquals(word, translated);
    }
}
