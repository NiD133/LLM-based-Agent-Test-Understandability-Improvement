package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.DateTimeException;
import java.time.temporal.TemporalAccessor;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test27 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * An InternationalFixedDate carries only date fields and no time-of-day or
     * zone information, so it cannot be converted into a ChronoZonedDateTime.
     * Passing one to zonedDateTime(TemporalAccessor) must therefore fail with a
     * DateTimeException raised from Chronology.
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromDateOnlyTemporalThrows() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;
        InternationalFixedDate dateOnly = chronology.dateYearDay(7, 7);

        try {
            chronology.zonedDateTime((TemporalAccessor) dateOnly);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Unable to obtain ChronoZonedDateTime from TemporalAccessor:
            // class org.threeten.extra.chrono.InternationalFixedDate
            verifyException("java.time.chrono.Chronology", e);
        }
    }
}
