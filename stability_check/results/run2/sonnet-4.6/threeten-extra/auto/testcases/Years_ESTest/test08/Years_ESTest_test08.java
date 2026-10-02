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
     * Verifies that calling addTo with zero years is a no-op:
     * the Years amount remains 0 after adding to a date obtained via IsoChronology.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Years zeroYears = Years.of(0);

        // Obtain IsoChronology via Period to create a date 217 days before the epoch
        Period periodMinusOneYear = Period.ofYears((-1));
        IsoChronology isoChronology = periodMinusOneYear.getChronology();
        ChronoLocalDate dateBeforeEpoch = isoChronology.dateEpochDay((-217L));

        // Adding zero years to a date is a no-op
        zeroYears.addTo(dateBeforeEpoch);

        assertEquals(0, zeroYears.getAmount());
    }
}
