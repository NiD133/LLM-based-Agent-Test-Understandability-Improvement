package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test26 extends Weeks_ESTest_scaffolding {

    /**
     * Adding a zero-length {@link Period} to {@link Weeks#ZERO} should yield
     * an amount of zero weeks.
     */
    @Test(timeout = 4000)
    public void plus_zeroPeriod_toZeroWeeks_returnsZeroWeeks() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        Period zeroPeriod = Period.ZERO;

        Weeks result = zeroWeeks.plus((TemporalAmount) zeroPeriod);

        assertEquals(0, result.getAmount());
    }
}
