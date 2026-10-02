package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test21 extends DayOfYear_ESTest_scaffolding {

    /**
     * Verifies that converting a DayOfYear to a LocalDate (via atYear) and back
     * again (via from) preserves the original day-of-year value.
     * <p>
     * Under the mocked system clock, DayOfYear.now() resolves to the 45th day
     * of the year, so the round-trip must also yield 45.
     */
    @Test(timeout = 4000)
    public void roundTripThroughLocalDatePreservesDayOfYear() throws Throwable {
        DayOfYear today = DayOfYear.now();

        LocalDate dateInYearOne = today.atYear(1);
        DayOfYear recovered = DayOfYear.from(dateInYearOne);

        assertEquals(45, recovered.getValue());
    }
}
