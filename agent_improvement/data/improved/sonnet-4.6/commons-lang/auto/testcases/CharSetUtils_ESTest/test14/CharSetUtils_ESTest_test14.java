package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test14 extends CharSetUtils_ESTest_scaffolding {

    // The input string "@~j'\"_*}sm" contains 'j', which also appears in the set pattern.
    // The set array intentionally has a null first element to verify that containsAny
    // tolerates null entries and still matches against the valid second element.
    @Test(timeout = 4000)
    public void test14_containsAny_withNullFirstSetEntry_returnsTrueWhenCharMatches() throws Throwable {
        String[] setPatterns = new String[2];
        setPatterns[0] = null;
        setPatterns[1] = "ZS[4!;6>G|3UPaJfj";

        boolean result = CharSetUtils.containsAny("@~j'\"_*}sm", setPatterns);

        assertTrue(result);
    }
}
