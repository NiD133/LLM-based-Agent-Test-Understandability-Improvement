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
public class DurationUtils_ESTest_test03 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#toChronoUnit(TimeUnit)} maps the
     * concurrency {@link TimeUnit#HOURS} to its temporal equivalent
     * {@link ChronoUnit#HOURS}.
     */
    @Test(timeout = 4000)
    public void toChronoUnit_withHours_returnsChronoUnitHours() throws Throwable {
        ChronoUnit result = DurationUtils.toChronoUnit(TimeUnit.HOURS);

        assertEquals(ChronoUnit.HOURS, result);
    }
}
