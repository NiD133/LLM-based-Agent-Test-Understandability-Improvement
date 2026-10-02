package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test28 extends Seconds_ESTest_scaffolding {

    // -19 minutes * 60 seconds/minute = -1140 seconds
    private static final int NEGATIVE_19_MINUTES_IN_SECONDS = -1140;

    @Test(timeout = 4000)
    public void test28_ofMinutes_negativeInput_convertsToSecondsAndHashCodeSucceeds() throws Throwable {
        Seconds negativeMinutes = Seconds.ofMinutes(-19);
        negativeMinutes.hashCode();
        assertEquals(NEGATIVE_19_MINUTES_IN_SECONDS, negativeMinutes.getAmount());
    }
}
