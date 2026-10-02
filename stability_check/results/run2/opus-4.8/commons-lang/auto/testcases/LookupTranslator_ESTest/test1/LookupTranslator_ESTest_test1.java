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
     * When the lookup table maps a term to itself, translating that exact term
     * returns the same text unchanged.
     */
    @Test(timeout = 4000)
    public void translateReturnsMappedValueForExactMatch() throws Throwable {
        String term = "FFFFFCCE";

        // A single lookup entry mapping the term to itself.
        // The row is oversized (length 3) so only the key/value slots [0] and [1] are used.
        CharSequence[] lookupEntry = new CharSequence[3];
        lookupEntry[0] = term;
        lookupEntry[1] = term;
        CharSequence[][] lookupTable = new CharSequence[1][7];
        lookupTable[0] = lookupEntry;

        LookupTranslator lookupTranslator = new LookupTranslator(lookupTable);

        String translated = lookupTranslator.translate(term);

        assertEquals(term, translated);
    }
}
