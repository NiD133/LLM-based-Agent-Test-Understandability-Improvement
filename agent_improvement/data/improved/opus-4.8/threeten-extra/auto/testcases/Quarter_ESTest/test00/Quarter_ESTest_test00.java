package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.chrono.HijrahDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test00 extends Quarter_ESTest_scaffolding {

    /**
     * {@link Quarter#adjustInto(java.time.temporal.Temporal)} only supports temporals that use the
     * ISO calendar system. A {@link HijrahDate} uses the Hijrah chronology, so adjusting it must
     * fail with a {@link DateTimeException} reporting the unsupported chronology.
     */
    @Test(timeout = 4000)
    public void adjustInto_nonIsoChronology_throwsDateTimeException() throws Throwable {
        Quarter firstQuarter = Quarter.Q1;
        HijrahDate nonIsoDate = MockHijrahDate.now();

        try {
            firstQuarter.adjustInto(nonIsoDate);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException expected) {
            // Message: "Adjustment only supported on ISO date-time"
            verifyException("org.threeten.extra.Quarter", expected);
        }
    }
}
