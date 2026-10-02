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
public class Years_ESTest_test23 extends Years_ESTest_scaffolding {

    /**
     * Subtracting a period of -1 year from Years.ZERO yields 1 year,
     * while the original zero-years instance stays unchanged (immutability).
     */
    @Test(timeout = 4000)
    public void subtractingNegativeOneYearPeriodFromZeroGivesOneYear() throws Throwable {
        Years zeroYears = Years.of(0);
        Period minusOneYear = Period.ofYears(-1);

        Years result = Years.ZERO.minus((TemporalAmount) minusOneYear);

        assertEquals(1, result.getAmount());
        assertTrue(zeroYears.isZero());
    }
}
