package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test20 extends Months_ESTest_scaffolding {

    /**
     * Verifies that {@link Months#from(java.time.temporal.TemporalAmount)} extracts the
     * month component from a {@link Period}.
     *
     * <p>A period of zero years with one month subtracted as {@code -1} months is
     * equivalent to a period of one month, so the resulting {@code Months} amount is 1.
     */
    @Test(timeout = 4000)
    public void from_periodOfOneMonth_returnsOneMonth() throws Throwable {
        Period oneMonth = Period.ofYears(0).minusMonths(-1L);

        Months result = Months.from(oneMonth);

        assertEquals(1, result.getAmount());
    }
}
