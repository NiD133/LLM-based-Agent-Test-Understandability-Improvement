package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.IsoChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test08 extends Years_ESTest_scaffolding {

    /**
     * Adding zero years to a date is a no-op: the Years object itself is
     * immutable and its amount stays 0 regardless of the target date.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Years zeroYears = Years.of(0);

        // Use a Period to obtain the ISO chronology, then build a date from epoch day -217
        Period negativeOnePeriod = Period.ofYears(-1);
        IsoChronology isoChronology = negativeOnePeriod.getChronology();
        ChronoLocalDate dateAtEpochDayMinus217 = isoChronology.dateEpochDay(-217L);

        // addTo with 0 years leaves both the date and the Years object unchanged
        zeroYears.addTo(dateAtEpochDayMinus217);

        assertEquals(0, zeroYears.getAmount());
    }
}
