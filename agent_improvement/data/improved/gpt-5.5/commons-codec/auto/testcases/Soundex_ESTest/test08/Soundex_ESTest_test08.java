package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test08 extends Soundex_ESTest_scaffolding {

    private static final String NUMERIC_MAPPING_TEXT = "01230120022455012623010202";

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Soundex genealogySoundex = Soundex.US_ENGLISH_GENEALOGY;

        int matchingEncodedCharacters = genealogySoundex.US_ENGLISH.difference(
                NUMERIC_MAPPING_TEXT,
                NUMERIC_MAPPING_TEXT);

        assertEquals(0, matchingEncodedCharacters);
    }
}
