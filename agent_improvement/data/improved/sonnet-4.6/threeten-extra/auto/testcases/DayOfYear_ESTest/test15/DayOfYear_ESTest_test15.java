package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.MinguoDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test15 extends DayOfYear_ESTest_scaffolding {

    /**
     * Verifies that DayOfYear.from() correctly extracts the day-of-year from a
     * MinguoDate. MockMinguoDate.now() returns a deterministic date corresponding
     * to day 45 of the year under EvoSuite's controlled clock.
     */
    @Test(timeout = 4000)
    public void test_fromMinguoDate_returnsCorrectDayOfYear() throws Throwable {
        MinguoDate minguoDate = MockMinguoDate.now();
        DayOfYear dayOfYear = DayOfYear.from(minguoDate);
        assertEquals(45, dayOfYear.getValue());
    }
}
