package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test02 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#toChronoUnit(TimeUnit)} maps the
     * {@link TimeUnit#DAYS} time unit to its equivalent {@link ChronoUnit#DAYS}.
     */
    @Test(timeout = 4000)
    public void toChronoUnit_withDays_returnsChronoUnitDays() throws Throwable {
        ChronoUnit chronoUnit = DurationUtils.toChronoUnit(TimeUnit.DAYS);

        assertEquals(ChronoUnit.DAYS, chronoUnit);
    }
}
