package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test21 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that {@link Quarter#from(java.time.temporal.TemporalAccessor)} maps
     * July to the third quarter (Q3), since July belongs to the July-September quarter.
     */
    @Test(timeout = 4000)
    public void from_julyMonth_returnsThirdQuarter() throws Throwable {
        Quarter quarterForJuly = Quarter.from(Month.JULY);

        assertEquals(Quarter.Q3, quarterForJuly);
    }
}
