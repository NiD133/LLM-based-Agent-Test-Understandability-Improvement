package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test0 extends LookupTranslator_ESTest_scaffolding {

    /**
     * When the input has no matching key in the lookup table, the translator
     * leaves the text unchanged and returns it as-is.
     *
     * Here the only mapping is "FFFFFFFF" -> "FFFFFFFF", so translating the
     * unrelated input "FFFFFCCE" produces the original string back.
     */
    @Test(timeout = 4000)
    public void translateReturnsInputUnchangedWhenNoKeyMatches() throws Throwable {
        final String unmatchedInput = "FFFFFCCE";

        // Lookup table: each row is a {key, value} pair. Both rows map
        // "FFFFFFFF" to "FFFFFFFF"; the unmatched input is not a key.
        CharSequence[] firstMapping = new CharSequence[4];
        firstMapping[0] = "FFFFFFFF";
        firstMapping[1] = "FFFFFFFF";

        CharSequence[] secondMapping = new CharSequence[5];
        secondMapping[0] = "FFFFFFFF";
        secondMapping[1] = "FFFFFFFF";
        secondMapping[3] = unmatchedInput;

        CharSequence[][] lookupTable = new CharSequence[2][6];
        lookupTable[0] = firstMapping;
        lookupTable[1] = secondMapping;

        LookupTranslator translator = new LookupTranslator(lookupTable);

        String result = translator.translate(unmatchedInput);

        assertEquals("FFFFFCCE", result);
    }
}
