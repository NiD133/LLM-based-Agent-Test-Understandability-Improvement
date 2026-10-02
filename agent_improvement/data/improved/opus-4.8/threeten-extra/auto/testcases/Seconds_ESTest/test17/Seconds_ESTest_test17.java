package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test17 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that {@code Seconds.ofMinutes(-1)} converts minutes to seconds
     * (-1 minute == -60 seconds) and that a non-zero amount is not reported as zero.
     */
    @Test(timeout = 4000)
    public void ofNegativeMinutes_convertsToSecondsAndIsNotZero() throws Throwable {
        Seconds minusOneMinute = Seconds.ofMinutes(-1);

        assertFalse("a -60 second amount must not be considered zero", minusOneMinute.isZero());
        assertEquals("-1 minute should equal -60 seconds", -60, minusOneMinute.getAmount());
    }
}
