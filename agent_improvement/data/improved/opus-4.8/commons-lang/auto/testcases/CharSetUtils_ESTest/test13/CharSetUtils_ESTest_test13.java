package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test13 extends CharSetUtils_ESTest_scaffolding {

    /**
     * When the character set consists entirely of null (empty) entries,
     * {@code containsAny} treats the set as empty and returns false,
     * regardless of the input string's contents.
     */
    @Test(timeout = 4000)
    public void containsAny_withSetOfOnlyNullEntries_returnsFalse() throws Throwable {
        String inputString = "@~j'\"_*}sm";
        String[] setOfNullEntries = new String[11];

        boolean foundAnyCharacter = CharSetUtils.containsAny(inputString, setOfNullEntries);

        assertFalse(foundAnyCharacter);
    }
}
