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
public class InternationalFixedChronology_ESTest_test16 extends InternationalFixedChronology_ESTest_scaffolding {

    // -719528 epoch days is exactly -DAYS_0000_TO_1970, corresponding to proleptic year 0.
    // The International Fixed calendar is only defined for years >= 1, so this triggers a DateTimeException.
    private static final long EPOCH_DAY_BEFORE_YEAR_ONE = -719528L;

    @Test(timeout = 4000)
    public void test_dateEpochDay_beforeYearOne_throwsDateTimeException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        try {
            chronology.dateEpochDay(EPOCH_DAY_BEFORE_YEAR_ONE);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
