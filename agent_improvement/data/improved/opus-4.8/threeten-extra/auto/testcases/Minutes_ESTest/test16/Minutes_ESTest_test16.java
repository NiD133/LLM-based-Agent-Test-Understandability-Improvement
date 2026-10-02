package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test16 extends Minutes_ESTest_scaffolding {

    /**
     * A non-zero number of hours converts to the equivalent positive minutes,
     * so the resulting amount is not considered zero.
     */
    @Test(timeout = 4000)
    public void ofHours_convertsToMinutesAndIsNotZero() throws Throwable {
        Minutes fiftyThreeThousandHours = Minutes.ofHours(53041);

        boolean isZero = fiftyThreeThousandHours.isZero();

        // 53041 hours * 60 minutes/hour = 3182460 minutes
        assertEquals(3182460, fiftyThreeThousandHours.getAmount());
        assertFalse(isZero);
    }
}
