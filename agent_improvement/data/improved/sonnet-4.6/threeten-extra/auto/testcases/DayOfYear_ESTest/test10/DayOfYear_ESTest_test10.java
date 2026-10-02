package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test10 extends DayOfYear_ESTest_scaffolding {

    /**
     * DayOfYear only supports DAY_OF_YEAR; querying any other ChronoField via
     * getLong() must throw UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void test_getLong_withUnsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        DayOfYear dayOfYear = DayOfYear.now();

        try {
            dayOfYear.getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
