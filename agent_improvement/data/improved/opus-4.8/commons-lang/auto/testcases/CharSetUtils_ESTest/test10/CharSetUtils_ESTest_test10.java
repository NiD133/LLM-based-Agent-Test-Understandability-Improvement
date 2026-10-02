package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test10 extends CharSetUtils_ESTest_scaffolding {

    /**
     * When the string to inspect is null, {@link CharSetUtils#count} should
     * return 0 regardless of the supplied character set. Here both the string
     * and the single set entry come from an uninitialized String array, so each
     * is null.
     */
    @Test(timeout = 4000)
    public void testCountWithNullStringReturnsZero() throws Throwable {
        String[] charSet = new String[1];
        String nullString = charSet[0];

        int count = CharSetUtils.count(nullString, charSet);

        assertEquals(0, count);
    }
}
