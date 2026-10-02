package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test14 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that containsAny returns true when the input string shares at
     * least one character with the supplied set. Here the set lists the
     * characters of "ZS[4!;6>G|3UPaJfj" (a null entry is ignored), and the
     * input string contains 'j', which is present in that set.
     */
    @Test(timeout = 4000)
    public void containsAnyReturnsTrueWhenStringSharesCharacterWithSet() throws Throwable {
        String[] characterSet = new String[2];
        characterSet[0] = null;
        characterSet[1] = "ZS[4!;6>G|3UPaJfj";

        boolean result = CharSetUtils.containsAny("@~j'\"_*}sm", characterSet);

        assertTrue(result);
    }
}
