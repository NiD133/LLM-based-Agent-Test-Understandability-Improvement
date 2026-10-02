package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test03 extends Years_ESTest_scaffolding {

    // Verifies that equals() is reflexive: a Years instance must equal itself.
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Years zeroYears = Years.of(0);
        boolean isEqualToItself = zeroYears.equals(zeroYears);
        assertTrue(isEqualToItself);
    }
}
