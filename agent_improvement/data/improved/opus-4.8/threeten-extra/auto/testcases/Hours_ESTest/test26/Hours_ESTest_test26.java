package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test26 extends Hours_ESTest_scaffolding {

    /**
     * Converting an empty (zero-length) Period to Hours should yield a zero
     * amount of hours. Computing its hash code must work without error, and
     * the resulting Hours value must report itself as zero.
     */
    @Test(timeout = 4000)
    public void fromZeroPeriodGivesZeroHours() throws Throwable {
        Hours hoursFromZeroPeriod = Hours.from(Period.ZERO);

        hoursFromZeroPeriod.hashCode();

        assertTrue(hoursFromZeroPeriod.isZero());
    }
}
