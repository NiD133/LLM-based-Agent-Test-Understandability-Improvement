package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test14 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkEquals(String, String)} returns false
     * when the first string is null and the second is non-null, since a null
     * value can never be equal to an actual string.
     */
    @Test(timeout = 4000)
    public void checkEqualsReturnsFalseWhenFirstStringIsNull() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        boolean nullEqualsString = systemCase.checkEquals(null, "phuL");

        assertFalse(nullEqualsString);
    }
}
