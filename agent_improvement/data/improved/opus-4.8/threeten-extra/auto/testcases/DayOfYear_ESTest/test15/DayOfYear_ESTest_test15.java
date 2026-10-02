package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import java.time.chrono.MinguoDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test15 extends DayOfYear_ESTest_scaffolding {

    /**
     * DayOfYear.from converts a non-ISO temporal (a Minguo calendar date) by first
     * mapping it to a LocalDate and then extracting the day-of-year field. The mocked
     * clock fixes "today" so the resulting day-of-year is deterministically 45.
     */
    @Test(timeout = 4000)
    public void from_minguoDate_extractsDayOfYearViaIsoConversion() throws Throwable {
        MinguoDate currentMinguoDate = MockMinguoDate.now();

        DayOfYear dayOfYear = DayOfYear.from(currentMinguoDate);

        assertEquals(45, dayOfYear.getValue());
    }
}
