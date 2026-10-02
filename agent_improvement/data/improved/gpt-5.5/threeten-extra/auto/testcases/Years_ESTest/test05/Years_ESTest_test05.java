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
public class Years_ESTest_test05 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Years zeroYears = Years.of(0);
        Period oneYearBefore = Period.ofYears((-1));
        IsoChronology isoChronology = oneYearBefore.getChronology();
        ChronoLocalDate originalDate = isoChronology.dateEpochDay((-217L));

        Temporal dateAfterSubtractingOneYear = zeroYears.ONE.subtractFrom(originalDate);

        assertNotSame(dateAfterSubtractingOneYear, originalDate);
    }
}
