package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test23 extends BritishCutoverChronology_ESTest_scaffolding {

    // An out-of-range month value (valid range is 1–12); used to verify input validation
    private static final int INVALID_MONTH = -992;
    private static final int ARBITRARY_YEAR = -992;
    private static final int ARBITRARY_DAY = -992;

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        BritishCutoverChronology britishCutoverChronology0 = new BritishCutoverChronology();

        // Supplying an invalid month should trigger DateTimeException from ValueRange validation
        try {
            britishCutoverChronology0.date(ARBITRARY_YEAR, INVALID_MONTH, ARBITRARY_DAY);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Expected: "Invalid value for MonthOfYear (valid values 1 - 12): -992"
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
