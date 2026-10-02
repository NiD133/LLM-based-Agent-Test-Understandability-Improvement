package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test13 extends CharSetUtils_ESTest_scaffolding {

    // containsAny returns false when the character set array contains only null elements
    @Test(timeout = 4000)
    public void test_containsAny_returnsFalse_whenSetArrayContainsOnlyNullElements() throws Throwable {
        String[] allNullCharSets = new String[11];
        boolean result = CharSetUtils.containsAny("@~j'\"_*}sm", allNullCharSets);
        assertFalse("containsAny should return false when all set entries are null", result);
    }
}
