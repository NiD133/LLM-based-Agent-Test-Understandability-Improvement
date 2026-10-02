package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test08 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Deleting characters from an empty string returns the empty string,
     * regardless of the character set supplied.
     */
    @Test(timeout = 4000)
    public void deleteFromEmptyStringReturnsEmptyString() throws Throwable {
        String[] charactersToDelete = new String[9];

        String result = CharSetUtils.delete("", charactersToDelete);

        assertEquals("", result);
    }
}
