package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test10 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testCountReturnsZeroForNullInputAndNullSetEntry() throws Throwable {
        String[] characterSetWithNullEntry = new String[1];

        int matchingCharacterCount = CharSetUtils.count(characterSetWithNullEntry[0], characterSetWithNullEntry);

        assertEquals(0, matchingCharacterCount);
    }
}
