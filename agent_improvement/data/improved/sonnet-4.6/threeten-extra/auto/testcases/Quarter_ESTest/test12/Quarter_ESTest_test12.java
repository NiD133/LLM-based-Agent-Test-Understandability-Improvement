package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test12 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.getLong(null) must throw NullPointerException because the implementation
     * calls field.getFrom(this) after the null field bypasses the QUARTER_OF_YEAR and
     * ChronoField checks, causing a NullPointerException inside Quarter.
     */
    @Test(timeout = 4000)
    public void test_getLong_nullField_throwsNullPointerException() throws Throwable {
        Quarter quarter = Quarter.Q2;
        try {
            quarter.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
