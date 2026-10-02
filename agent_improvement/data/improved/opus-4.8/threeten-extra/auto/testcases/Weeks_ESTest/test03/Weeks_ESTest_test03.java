package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test03 extends Weeks_ESTest_scaffolding {

    /**
     * Weeks.equals should return false when compared against an object
     * that is not a Weeks instance (here, a plain Object).
     */
    @Test(timeout = 4000)
    public void equals_returnsFalse_whenComparedToNonWeeksObject() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        Object nonWeeksObject = new Object();

        boolean isEqual = zeroWeeks.equals(nonWeeksObject);

        assertFalse(isEqual);
    }
}
