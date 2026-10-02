package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test21 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#value(IOCase, IOCase)} returns the first
     * argument when it is non-null (ignoring the default), and that the
     * returned constant can then be used for a case-sensitivity aware
     * comparison.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        final IOCase defaultCase = IOCase.SYSTEM;
        final IOCase preferredCase = IOCase.INSENSITIVE;

        // value() keeps the preferred (non-null) constant rather than the default.
        final IOCase resolvedCase = IOCase.value(preferredCase, defaultCase);
        assertEquals(IOCase.INSENSITIVE, resolvedCase);

        // The resolved INSENSITIVE constant is not the SYSTEM default.
        assertNotSame(defaultCase, resolvedCase);

        // Comparing a string with itself is equal regardless of case sensitivity.
        resolvedCase.checkCompareTo(
                "org.apache.commons.io.Filena+eUtils",
                "org.apache.commons.io.Filena+eUtils");
    }
}
